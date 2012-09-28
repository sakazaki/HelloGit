import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * HTTPテストクラスです。
 *
 * @author 3567
 *
 */
public class HttpTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
//            httpclient.getCredentialsProvider().setCredentials(
//                    new AuthScope("192.168.254.135", 80),
//                    new UsernamePasswordCredentials("3567", "Passw0rd"));
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope("192.168.254.135", 80),
                    new NTCredentials("3567", "Passw0rd", "tg111110", "tsuzuki"));

            HttpHost targetHost = new HttpHost("www.verisign.com", 443, "https");
            HttpHost proxy = new HttpHost("192.168.254.135", 80);

            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

            HttpGet httpget = new HttpGet("/");

            System.out.println("executing request: " + httpget.getRequestLine());
            System.out.println("via proxy: " + proxy);
            System.out.println("to target: " + targetHost);

            HttpResponse response = httpclient.execute(targetHost, httpget);
            HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
            }
            EntityUtils.consume(entity);

        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }

	}

}
