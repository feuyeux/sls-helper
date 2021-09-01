import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.exception.LogException;
import com.aliyun.openservices.log.response.ListProjectResponse;
import java.util.List;

public class Main {

  static String accessId = "";
  static String accessKey = "";
  static List<String> hosts = List.of(
      "cn-beijing.log.aliyuncs.com",
      "cn-hangzhou.log.aliyuncs.com",
      "cn-zhangjiakou.log.aliyuncs.com",
      "cn-shenzhen.log.aliyuncs.com",
      "cn-huhehaote.log.aliyuncs.com",
      "eu-central-1.log.aliyuncs.com",
      "cn-chengdu.log.aliyuncs.com");

  public static void main(String[] args) {
    hosts.forEach(host -> {
      Client client = new Client(host, accessId, accessKey);
      ListProjectResponse listProjectResponse = null;
      try {
        listProjectResponse = client.ListProject();
      } catch (LogException e) {
        e.printStackTrace();
      }
      if (listProjectResponse != null) {
        int count = listProjectResponse.getCount();
        System.out.println(host + ":" + count);
        if (count > 0) {
          listProjectResponse.getProjects().forEach(p -> {
            try {
              System.out.println("Delete" + p.getProjectName());
              client.DeleteProject(p.getProjectName());
            } catch (LogException e) {
              e.printStackTrace();
            }
          });
        }
      }
    });
  }
}
