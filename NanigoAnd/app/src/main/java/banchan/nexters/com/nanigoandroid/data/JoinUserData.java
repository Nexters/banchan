package banchan.nexters.com.nanigoandroid.data;

/**
 * Created by Ellen on 2018-08-19.
 */

   /**
 * {
 * "age": 25,
 * "deviceKey": "device key",
 * "sex": "F",
 * "username": {
 * "prefix": "슬픈",
 * "postfix": "친칠라"
 * }
 * }
 */

public class JoinUserData {

   private String age;
   private String sex;
   private String deviceKey;
     private UserName username;

         public String getAge(){
        return age;
        }
        public void setAge(String age){
            this.age=age;
        }


       public String getSex(){
        return sex;
        }
        public void setSex(String sex){
            this.sex=sex;
        }

       public String getDevicekey(){
        return deviceKey;
        }
        public void setDevicekey(String deviceKey){
            this.deviceKey=deviceKey;
        }

            public String getUserName(){
        return username.getPrefix()+" "+username.getPostfix();
        }
        public void setUserName(String prefix, String postfix){
             UserName name = new UserName();
             name.setPrefix(prefix);
             name.setPostfix(postfix);
            this.username=name;
        }

    class UserName{
        private String prefix;
        private String postfix;

        public String getPrefix(){
        return prefix;
        }
        public void setPrefix(String prefix){
            this.prefix=prefix;
        }
        public String getPostfix(){
        return postfix;
        }
        public void setPostfix(String postfix){
            this.postfix=postfix;
        }
    }
}
