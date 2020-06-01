package life.majiang.community.provider;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class OosProvider {
    @Value("${oss.accessKeyId}") //读取配置文件中的值
    private String accessKeyId;

    @Value("${oss.accessKeySecret}") //读取配置文件中的值
    private String accessKeySecret;
    @Value("${oss.bucketName}") //读取配置文件中的值
    private String bucketName;
    @Value("${oss.endpoint}") //读取配置文件中的值
    public String endpoint;

    public String uploadFile(InputStream fileStream, String fileName) {
        String generatedFileName;
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {

            ossClient.putObject(bucketName, generatedFileName, fileStream);
            OSSObject object = ossClient.getObject(new GetObjectRequest(bucketName, generatedFileName));
//            上传成功返回图片url
            if(object.getResponse().getStatusCode()==200){
                Date expiration = new Date(new Date().getTime() + 3600 * 1000);
                // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
                URL url = ossClient.generatePresignedUrl(bucketName, generatedFileName, expiration);
                return url.toString();
            }else {
                throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
            }

        } catch (OSSException oe) {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (ClientException ce) {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            ossClient.shutdown();
        }

    }

}
