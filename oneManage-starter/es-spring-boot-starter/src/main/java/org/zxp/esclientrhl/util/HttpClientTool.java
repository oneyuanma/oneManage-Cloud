package org.zxp.esclientrhl.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.SSLContexts;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import javax.net.ssl.SSLContext;

/**
 * @program: esclientrhl
 * @description: http???????????????
 * @author: X-Pacific zhang
 * @create: 2019-10-10 12:53
 **/
public class HttpClientTool {
    private static HttpClient mHttpClient = null;

    private static CloseableHttpClient getHttpClient(HttpClientBuilder httpClientBuilder) {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        //??????????????????????????????????????????????????????
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //??????????????????
            TrustStrategy anyTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        //?????????????????????
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        //???????????????
        return httpClientBuilder.setConnectionManager(connManager).build();
    }

    private synchronized static HttpClient getESHttpClient() {
        if (mHttpClient == null) {
//            HttpParams params = new BasicHttpParams();
//            //??????????????????
//            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//            HttpProtocolParams.setContentCharset(params, Constants.CHARSET);
//            HttpProtocolParams.setUseExpectContinue(params, true);
//            //????????????
//            /*???????????????????????????????????????*/
//            ConnManagerParams.setTimeout(params, Constants.CONMANTIMEOUT);
//            /*????????????*/
//            HttpConnectionParams.setConnectionTimeout(params, Constants.CONTIMEOUT);
//            /*????????????*/
//            HttpConnectionParams.setSoTimeout(params, Constants.SOTIMEOUT);
//            //??????HttpClient??????HTTp???HTTPS????????????
//            SchemeRegistry schReg = new SchemeRegistry();
//            schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//            schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
//            PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schReg);
//            cm.setMaxTotal(Constants.MAXTOTAL);
//            cm.setDefaultMaxPerRoute(Constants.DEFAULTMAXPERROUTE);
//            mHttpClient = new DefaultHttpClient(cm,params);
            mHttpClient = getHttpClient(HttpClientBuilder.create());
        }
        return mHttpClient;
    }

    private synchronized static HttpClient getESHttpClient(String username,String password){
        if(mHttpClient == null){
            mHttpClient = getHttpClientWithBasicAuth(username, password);
        }
        return mHttpClient;
    }

    private static HttpClientBuilder credential(String username, String password) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CredentialsProvider provider = new BasicCredentialsProvider();
        AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        provider.setCredentials(scope, credentials);
        httpClientBuilder.setDefaultCredentialsProvider(provider);
        return httpClientBuilder;
    }

    /**
     * ????????????basic Auth?????????HttpClient
     * @param username
     * @param password
     * @return
     */
    private static CloseableHttpClient getHttpClientWithBasicAuth(String username, String password){
        return getHttpClient(credential(username, password));
    }

    //??????????????????e.g. content-type ???
    private static void setHeaders(HttpRequestBase req, Map<String, String> headers){
        if(headers == null){
            return;
        }
        for(Map.Entry<String, String> header : headers.entrySet()){
            req.setHeader(header.getKey(), header.getValue());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * ??????http??????
     *
     * @param url
     * @param obj
     * @return
     * @throws Exception
     */
    public static String execute(String url, String obj) throws Exception {
        HttpClient httpClient = null;
        HttpResponse response = null;
        httpClient = HttpClientTool.getESHttpClient();
        HttpUriRequest request = postMethod(url, obj);
        response = httpClient.execute(request);
        HttpEntity entity1 = response.getEntity();
        String respContent = EntityUtils.toString(entity1, HTTP.UTF_8).trim();
        return respContent;
    }

    /**
     * ??????http??????
     *
     * @param url
     * @param obj
     * @return
     * @throws Exception
     */
    public static String execute(String url, String obj, String username, String password) throws Exception {
        HttpClient httpClient = null;
        HttpResponse response = null;
        httpClient = HttpClientTool.getESHttpClient(username, password);
        HttpUriRequest request = postMethod(url, obj);
        response = httpClient.execute(request);
        HttpEntity entity1 = response.getEntity();
        String respContent = EntityUtils.toString(entity1, HTTP.UTF_8).trim();
        return respContent;
    }

    private static HttpUriRequest postMethod(String url, String data)
            throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        if (data != null) {
            httpPost.setEntity(new StringEntity(data, "UTF-8"));
        }
        httpPost.addHeader("Content-Type", "application/json");
        return httpPost;
    }

//    static class Constants {
//        /** ??????*/
//        public static final String CHARSET = HTTP.UTF_8;
//        /*???????????????????????????????????????*/
//        public static final int CONMANTIMEOUT = 2000;
//        /*????????????*/
//        public static final int CONTIMEOUT = 2000;
//        /*????????????*/
//        public static final int SOTIMEOUT = 5000;
//        /*????????????????????????????????????*/
//        public static final int MAXTOTAL = 6;
//        /*???????????????????????????MaxTotal???????????????*/
//        public static final int DEFAULTMAXPERROUTE = 3;
//    }
}
