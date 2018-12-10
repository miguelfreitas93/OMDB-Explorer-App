import sys
import requests
import time
import xmltodict
 
args = sys.argv
requests_rate = 10 #seconds
 
if len(args) < 12 or len(args) > 15:
    print(args)
    print("Missing Arguments : this script should only include 7 parameters: "
          "\n<Server>"  # Server URL Ex.: http://localhost
          "\n<cxUsername>"  # Cx Username
          "\n<cxPassword>"  # Cx Password
          "\n<Client Secret>"  # Client Secret
          "\n<Project Name>"  # Cx Project Name
          "\n<Team Name>"  # Cx Team Name
          "\n<Preset Name>"  # Cx Preset Name
          "\n<Folder Exclusions>"  # Folder Exclusions
          "\n<File Exclusions>"  # File Exclusions
          "\n<GIT Repo URL>"  # GIT Repository URL
          "\n<GIT Repo Branch>"  # GIT Repository Branch
          "\n<High Threshold (Optional)>"  # Minimum Number of High Results allowed, if higher build will fail
          "\n<Medium Threshold (Optional)>"  # Minimum Number of Medium Results allowed, if higher build will fail
          "\n<Low Threshold (Optional)>")  # Minimum Number of Low Results allowed, if higher build will fail
    exit(1)
else:
    server = args[1]
    cxUsername = args[2]
    cxPassword = args[3]
    clientSecret = args[4]
    project_name = args[5]
    team_name = args[6]
    preset_name = args[7]
    folder_exclusions = args[8]
    file_exclusions = args[9]
    git_repo_url = args[10]
    git_repo_branch = "refs/heads/" + args[11]
    git_private_key = ""
    script_name = "TravisCI_Script"
    print(args)
 
    def get_threshold(args_list, index):
        if len(args_list) > index and args_list[index]:
            return int(args_list[index])
        else:
            return 0
 
    highThreshold = get_threshold(args, 12)
    mediumThreshold = get_threshold(args, 13)
    lowThreshold = get_threshold(args, 14)
 
    endpoint_server = server + "/cxrestapi/"
    print(endpoint_server)
 
    def get_oauth2_token():
        oauth2_data = {
            "username": cxUsername,
            "password": cxPassword,
            "grant_type": "password",
            "scope": "sast_rest_api",
            "client_id": "resource_owner_client",
            "client_secret": clientSecret
        }
        oauth2_response = requests.post(endpoint_server + "/auth/identity/connect/token", data=oauth2_data)
        if oauth2_response.status_code == 200:
            json = oauth2_response.json()
            return json["token_type"] + " " + json["access_token"]
        else:
            return False
 
 
    token = get_oauth2_token()
    headers = {
        "Authorization": token
    }
 
 
    def print_status(status_name, project_id, scan_id, report_id=None):
        if report_id:
            print(status_name + " - Project - " + str(project_id) + " - Scan ID - " + scan_id + " - Report ID - " +
                  report_id)
        else:
            print(status_name + " - Project - " + str(project_id) + " - Scan ID - " + scan_id)
 
 
    def error(resp):
        print("Error - " + str(resp.status_code) + " :\n" + resp.text)
        return None
 
 
    def get_team_by_name(team_name):
        teams_response = requests.get(endpoint_server + "/auth/teams", headers=headers)
        if teams_response.status_code == 200:
            teams = teams_response.json()
            print(teams)
            for team in teams:
                print(team)
                if team['fullName'] == team_name:
                    return team['id']
            return []
        else:
            error(teams_response)
            return None
 
 
    def create_project(project_name, team_id):
        create_project_data = {
            "name": project_name,
            "owningTeam": team_id,
            "isPublic": True
        }
        project_response = requests.post(endpoint_server + "/projects", headers=headers, data=create_project_data)
        if project_response.status_code == 201:
            return project_response.json()
        else:
            return False
 
 
    def set_project_git_settings(project_id, git_repo_url, git_repo_branch, git_private_key):
        git_settings_data = {
            "url": git_repo_url,
            "branch": git_repo_branch,
            "privateKey": git_private_key
        }
        git_settings_response = requests.post(
            endpoint_server + "/projects/" + str(project_id) + "/sourceCode/remoteSettings/git", headers=headers,
            data=git_settings_data)
        if git_settings_response.status_code == 204:
            return True
        else:
            error(git_settings_response)
            return False
 
 
    def set_project_exclude_settings(project_id, exclude_folders, exclude_files):
 
        exclude_settings_data = {
            "excludeFoldersPattern": exclude_folders,
            "excludeFilesPattern": exclude_files
        }
        exclude_settings_response = requests.put(
            endpoint_server + "/projects/" + str(project_id) + "/sourceCode/excludeSettings", headers=headers,
            data=exclude_settings_data)
        if exclude_settings_response.status_code == 200:
            return True
        else:
            error(exclude_settings_response)
            return False
 
 
    def get_projects_by_name(project_name):
 
        projects_response = requests.get(endpoint_server + "/projects", headers=headers)
        if projects_response.status_code == 200:
            projects = projects_response.json()
            for proj in projects:
                if proj['name'] == project_name:
                    return proj['id']
            return False
        else:
            error(projects_response)
            return False
 
 
    def get_preset_by_name(preset_name):
 
        presets_response = requests.get(endpoint_server + "/sast/presets", headers=headers)
        if presets_response.status_code == 200:
            presets = presets_response.json()
            for preset in presets:
                if preset['name'] == preset_name:
                    return preset['id']
            return []
        else:
            error(presets_response)
            return False
 
 
    def get_engine_server():
 
        engine_response = requests.get(endpoint_server + "/sast/engineServers", headers=headers)
        if engine_response.status_code == 200:
            engines = engine_response.json()
            return engines[0]['id']
        else:
            error(engine_response)
            return False
 
 
    def update_project_configuration(project_id, preset_id, engine_id):
 
        project_config_data = {
            "projectId": project_id,
            "presetId": preset_id,
            "engineConfigurationId": engine_id
        }
        project_config_response = requests.post(endpoint_server + "/sast/scanSettings", headers=headers,
                                                data=project_config_data)
        if project_config_response.status_code == 200:
            return True
        else:
            error(project_config_response)
            return False
 
 
    def scan_project(project_id, project_name):
 
        data = {
            "projectId": project_id,
            "isIncremental": False,
            "isPublic": True,
            "forceScan": True
        }
        headersScanProject = {
            "Authorization": token,
            "cxOrigin": script_name
        }
        start_scan_response = requests.post(endpoint_server + "/sast/scans", headers=headersScanProject, data=data)
        if start_scan_response.status_code == 201:
            scan = start_scan_response.json()
            scan_id = str(scan["id"])
            status = "New"
            print_status(status, project_name, scan_id)
            past_status = status
            while status != "Finished":
                time.sleep(requests_rate)
                get_scan_response = requests.get(endpoint_server + scan["link"]["uri"], headers=headers)
                if get_scan_response.status_code == 200:
                    status = get_scan_response.json()["status"]["name"]
                    if past_status != status:
                        print_status(status, project_name, scan_id)
                        past_status = status
                else:
                    return error(get_scan_response)
            print_status("Scan Finished", project_name, scan_id)
            return scan_id
 
 
    def generate_report(scan_id, project_name):
 
        report_request_data = {
            "reportType": "XML",
            "scanId": scan_id
        }
        new_scan_report_response = requests.post(endpoint_server + "/reports/sastScan", headers=headers,
                                                 data=report_request_data)
        if new_scan_report_response.status_code == 202:
            report = new_scan_report_response.json()
            report_id = str(report["reportId"])
            status = "InProcess"
            past_status = status
            print_status(status, project_name, scan_id, report_id)
            while status != "Created":
                time.sleep(requests_rate)
                get_report_status_response = requests.get(endpoint_server + report["links"]["status"]["uri"],
                                                          headers=headers)
                if get_report_status_response.status_code == 200:
                    status = get_report_status_response.json()["status"]["value"]
                    if past_status != status:
                        print_status(status, project_name, scan_id, report_id)
                        past_status = status
                else:
                    return error(get_report_status_response)
            print("Report Generated - " + report_id)
            get_report_response = requests.get(endpoint_server + report["links"]["report"]["uri"],
                                               headers=headers)
            if get_report_response.status_code == 200:
                return get_report_response.text
            else:
                return error(get_report_response)
        else:
            return error(new_scan_report_response)
 
 
    def parse_xml(doc, high_threshold, medium_threshold, low_threshold):
        queries = []
        languages = []
        highs = 0
        mediums = 0
        lows = 0
        infos = 0
        confirmed = 0
        not_exploitable = 0
        to_verify = 0
        highs_to_verify = 0
        mediums_to_verify = 0
        lows_to_verify = 0
        infos_to_verify = 0
 
        if doc and 'CxXMLResults' in doc:
            xml_results = doc['CxXMLResults']
 
            if xml_results and 'Query' in xml_results:
                for query in xml_results['Query']:
                    results = query['Result']
                    list_results = []
                    if isinstance(results, list):
                        list_results = results
                    else:
                        list_results.append(results)
                    for result in list_results:
                        state = result["@state"]
                        if state == "0":
                            to_verify += 1
                        elif state == "1":
                            not_exploitable += 1
                        elif state == "2":
                            confirmed += 1
 
                        severity = result["@Severity"]
 
                        if severity == "High":
                            highs += 1
                            if state == "0":
                                highs_to_verify += 1
                        elif severity == "Medium":
                            mediums += 1
                            if state == "0":
                                mediums_to_verify += 1
                        elif severity == "Low":
                            lows += 1
                            if state == "0":
                                lows_to_verify += 1
                        elif severity == "Information":
                            infos += 1
                            if state == "0":
                                infos_to_verify += 1
                    queries.append(query)
                    if query["@Language"] not in languages:
                        languages.append(query["@Language"])
 
            total = highs + mediums + lows + infos
 
            deep_link = xml_results["@DeepLink"]
            deep_link = deep_link.replace("http://localhost", server)
            print("\nScan Link : " + deep_link)
            print("Project Name : " + xml_results["@ProjectName"])
            print("Project ID : " + xml_results["@ProjectId"])
            print("Preset : " + xml_results["@Preset"])
            print("LOC : " + xml_results["@LinesOfCodeScanned"])
            print("Files Count : " + xml_results["@FilesScanned"])
            print("CX Version : " + xml_results["@CheckmarxVersion"])
            print("Team : " + xml_results["@TeamFullPathOnReportDate"])
            print("Owner : " + xml_results["@Owner"])
            print("\nInitiator : " + xml_results["@InitiatorName"])
            print("Scan ID : " + xml_results["@ScanId"])
            print("Scan Type : " + xml_results["@ScanType"])
            print("Scan Comments : " + xml_results["@ScanComments"])
            print("Source Origin : " + xml_results["@SourceOrigin"])
            print("Scan Start : " + xml_results["@ScanStart"])
            print("Scan Time : " + xml_results["@ScanTime"])
            print("Visibility : " + xml_results["@Visibility"])
            print("Report Creation Date : " + xml_results["@ReportCreationTime"])
            print("\nResults (" + str(total) + ") : ")
            print("\tHigh : " + str(highs))
            print("\tMedium : " + str(mediums))
            print("\tLow : " + str(lows))
            print("\tInfo : " + str(infos))
            print("\nConfirmed : " + str(confirmed))
            print("Not Exploitable : " + str(not_exploitable))
            print("To Verify (" + str(to_verify) + ") : ")
            print("\tHigh : " + str(highs_to_verify))
            print("\tMedium : " + str(mediums_to_verify))
            print("\tLow : " + str(lows_to_verify))
            print("\tInfo : " + str(infos_to_verify))
            print("\nLanguages (" + str(len(languages)) + ") :")
            for lang in languages:
                print("\t" + lang)
 
            print("\nQueries (" + str(len(queries)) + ") :")
            for query in queries:
                print("\t" + query["@name"] + " (" + str(len(query["Result"])) + ")")
 
            if highs > high_threshold or mediums > medium_threshold or lows > low_threshold:
                print("\n\nERROR : Insecure application !!!")
                exit(0)
            else:
                print("\n\nSUCCESS : Secure application !!!")
                exit(0)
        else:
            print("Error retrieving the XML Results")
            exit(3)
 
 
    project_id = "0"
    team_id = get_team_by_name(team_name)
 
    if team_id:
        project_was_created = create_project(project_name, team_id)
        if project_was_created:
            print("Project Created")
            project_id = str(project_was_created['id'])
        else:
            print("Project Already Exists")
            project_id = str(get_projects_by_name(project_name))
     
        print("Project : " + project_name + " - " + project_id)
        git_settings_updated = set_project_git_settings(project_id, git_repo_url, git_repo_branch, git_private_key)
        if git_settings_updated:
            print("Git Setting Updated")
        else:
            print("Git Setting Error")
     
        exclude_settings_updated = set_project_exclude_settings(project_id, folder_exclusions, file_exclusions)
        if exclude_settings_updated:
            print("Exclude Setting Updated")
        else:
            print("Exclude Setting Error")
     
        preset_id = str(get_preset_by_name(preset_name))
     
        engine_id = str(get_engine_server())
     
        project_updated = update_project_configuration(project_id, preset_id, engine_id)
        if project_updated:
            print("Project Configuration Updated")
        else:
            print("Project Configuration Error")
     
        scan_id = scan_project(project_id, project_name)
     
        xml = generate_report(scan_id, project_name)
     
        if xml:
            document = xmltodict.parse(xml)
            parse_xml(document, highThreshold, mediumThreshold, lowThreshold)
        else:
            print("Error retrieving the XML Results")
            exit(2)
    else:
        print("Invalid Team Name")
        print(team_name)
        exit(2)