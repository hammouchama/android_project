    package com.rajendra.courseapp.retrofit;


    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;

    public class RetrofitClient {

        private  static Retrofit retrofit;
        //private static final String BASE_URL = "https://androidappsforyoutube.s3.ap-south-1.amazonaws.com/course_app/";
        private static final String BASE_URL="http://192.168.1.105:8084/api/";
        public static Retrofit getRetrofitClient(){


            if(retrofit == null){
                System.out.println(retrofit);

                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;

        }

      /*  static Retrofit getClient() {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            if(retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
            }


            return retrofit;
        }
*/
    }
