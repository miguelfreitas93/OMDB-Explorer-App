package mf.omdb.app.core.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import mf.omdb.app.core.interfaces.OmdbApiInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(JUnit4.class)
public class CommonUtilsTest {

	@Test
	public void verifyIf2017IsTheActualYear() throws Exception {
		String actualYear = "2017";

		boolean result = CommonUtils.isActualYear(actualYear);
		assertEquals(result, true);
	}

	@Test
	public void verifyIf2016IsNotTheActualYear() throws Exception {
		String actualYear = "2016";

		boolean result = CommonUtils.isActualYear(actualYear);
		assertEquals(result, false);
	}

	@Test
	public void verifyIfMethodSupportYearStringWithErrors() throws Exception {
		String actualYear = "2016-";

		boolean result = CommonUtils.isActualYear(actualYear);
		assertEquals(result, false);

		actualYear = "2017-";

		result = CommonUtils.isActualYear(actualYear);
		assertEquals(result, true);
	}

	@Test
	public void verifyIfRetrofitReturnsANewInstanceOfOMDBApiInterface() throws Exception {

		Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.API_URL)
				.addConverterFactory(GsonConverterFactory.create()).build();

		OmdbApiInterface newOmdbApiInterface = retrofit.create(OmdbApiInterface.class);

		OmdbApiInterface omdbApiInterface = CommonUtils.getOmdbApiInterface();
		assertNotEquals(omdbApiInterface, newOmdbApiInterface);
	}

}