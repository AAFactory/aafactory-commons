package retrofit2;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Created by hanjoong on 2018-05-05.
 */

public class DynamicBaseUrlTest {
	
	@Test
	public void hostSelectionInterceptor() throws IOException {
		HostSelectionInterceptor hostSelectionInterceptor = new HostSelectionInterceptor();

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.addInterceptor(hostSelectionInterceptor)
				.build();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://www.coca-cola.com/")
				.callFactory(okHttpClient)
				.build();

		Pop pop = retrofit.create(Pop.class);

		Response<ResponseBody> response1 = pop.robots().execute();
		System.out.println("Response from: " + response1.raw().request().url());

		hostSelectionInterceptor.setHost("www.pepsi.com");
		Response<ResponseBody> response2 = pop.robots().execute();
		Assert.assertEquals("http://www.pepsi.com/robots.txt", StringUtils.trim(response2.raw().request().url().toString()));
	}

	public interface Pop {
		@GET("robots.txt")
		Call<ResponseBody> robots();
	}
	
	static final class HostSelectionInterceptor implements Interceptor {
		private volatile String host;

		public void setHost(String host) {
			this.host = host;
		}

		@Override public okhttp3.Response intercept(Chain chain) throws IOException {
			Request request = chain.request();
			String host = this.host;
			if (host != null) {
				HttpUrl newUrl = request.url().newBuilder()
						.host(host)
						.build();
				request = request.newBuilder()
						.url(newUrl)
						.build();
			}
			return chain.proceed(request);
		}
	}
}
