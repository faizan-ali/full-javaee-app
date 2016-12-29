package co.workamerica.functionality.aws.s3;


import co.workamerica.functionality.shared.utilities.Clock;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.InputStream;


/**
 * Created by Faizan on 7/25/2016.
 */
public class S3Object {

    private static final String name = "";

    public static String webHookDump(InputStream stream, String school, String extension) {
        if (stream != null) {
            extension = extension == null || extension.isEmpty() ? ".xml" : extension.contains(".") ? extension : "." + extension;
            String fileName = "webhooks/" + school + "/" + school + "_" + Clock.getCurrentDateDashes() + "_" + Clock.getCurrentTime() + extension;
            AmazonS3 s3 = new AmazonS3Client();
            Region region = Region.getRegion(Regions.US_WEST_2);
            s3.setRegion(region);
            try {
                File file = CustomUtilities.inputStreamToFile(stream);
                s3.putObject(new PutObjectRequest(name, fileName, file));
                return CustomUtilities.fileToString(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
