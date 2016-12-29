package co.workamerica.functionality.shared.utilities;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.XML;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 * This class contains a few custom written utilities used throughout
 * the platform.
 */
public class CustomUtilities {

    public CustomUtilities() {

    }

    // Checks if a given date in the format MM/DD/YYYY has already passed
    public static boolean hasDatePassed(String date) {
        if (date != null && !date.isEmpty()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            try {
                LocalDate today = LocalDate.parse(Clock.getCurrentDate(), dtf),
                        target = LocalDate.parse(date, dtf);
                if (today.isAfter(target)) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Takes a String representation of an XML file and returns it parsed as a JSON Object
    public static JSONObject XMLToJSONObject(String xml) {
        if (xml != null && !xml.isEmpty()) {
            try {
                return XML.toJSONObject(xml);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Takes an InputStream and returns a file (the stream is closed)
    public static File inputStreamToFile(InputStream stream) {
        if (stream != null) {
            File tmp = new File(Clock.getCurrentDate() + "_" + Clock.getCurrentTime());
            tmp.deleteOnExit();
            try {
                FileUtils.copyInputStreamToFile(stream, tmp);
                return tmp;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Takes a File and returns a String representation
    public static String fileToString(File file) {
        if (file != null) {
            try {
                return Files.toString(file, Charsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Takes a phone number and returns it without any non-numeric characters or whitespace
    public static String cleanNumber(String phone) {
        return phone == null ? "" : phone.trim().replaceAll("[^0-9]+", "");
    }

    // Checks if a phone number's length is 10 after stripping non-numeric characters
    public static boolean isValidNumber(String phone) {
        return cleanNumber(phone).length() == 10;
    }

    // Checks if a Zip is numeric and length is 5
    public static boolean isValidZip(String zip) {
        return zip != null && zip.length() == 5 && isNumeric(zip);
    }

    // Checks if an email contains an '@' and a '.'
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    // Generates a random alpha-numeric String of length parameter
    public static String randomStringGenerator(int length) {
        final String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(str.charAt(random.nextInt(str.length())));
        return sb.toString();
    }

    // Returns a String representing Array values seperated by commas
    public static String arrayAsString(String[] array) {
        String newString = "";

        if (array == null) {
            return "";
        } else if (array.length == 0) {
            return "";
        }

        for (String elt : array) {
            newString += elt + ",";
        }

        newString = newString.substring(0, newString.length() - 1);

        return newString;
    }

    // Returns a new String with the first letter of the parameter capitalized
    public static String capitalizeFirstLetter(String word) {

        if (word != null && word.length() > 1) {
            return word.substring(0, 1).toUpperCase() + word.substring(1);
        } else {
            return "";
        }
    }

    // Takes a String password and a Byte Array representing a salt (which can
    // be null)
    // If the salt is null, generates a new salt to hash the password, otherwise
    // uses parameter salt
    // Returns a String Array where [0] is the hashed password and [1] is the
    // salt
    public static String[] hashPassword(final String passwordString, byte[] saltBytes) {

      
    }

    // Converts a Byte Array to a hexadecimal String, helper function for
    // hashPassword
    // Thanks Stackoverflow for an efficient as fuck solution
    public static String bytesToHex(byte[] bytes) {
       
    }

    // Converts a hexadecimal String to a Byte Array, used to compare hashed
    // password for authentication
    public static byte[] hexToBytes(String hex) {
        return DatatypeConverter.parseHexBinary(hex);
    }

    // Checks if a string is purely numeric
    public static boolean isNumeric(String string) {
        return string != null && string.matches("^\\d+$");
    }

    //	Gets the file type and returns a string
    public static String getFileType(FileItemStream file) {
        if (file != null) {
            String fileName = file.getName().toLowerCase();
            String[] splitName = fileName.split("\\.", -1);
            String lastSplit = "." + splitName[splitName.length - 1];
            return lastSplit;
        }
        return null;
    }

    //	Takes takes a fileItemStream and a doctype -> checks the doctype and returns a byteArray.
    public static byte[] fileItemStreamToByteArray(FileItemStream item, int docType) throws IOException, FileUploadException {
        if (item != null && docType > 0) {
            byte[] array = IOUtils.toByteArray(item.openStream());
            String[] types = new String[0];
            boolean returnVal = false;

            if (docType == 1) {
                types = new String[]{".jpg", ".jpeg", ".png"};
            } else if (docType == 2) {
                types = new String[]{".pdf", ".doc", ".docx"};
            } else if (docType == 3) {
                types = new String[]{".pdf", ".jpg", ".jpeg", ".png"};
            }

            for (String type : types) {
                if (getFileType(item).contains(type)) {
                    returnVal = true;
                }
            }

            if (item.getName() != null && returnVal) {
                return array;
            }


        }
        throw new InvalidFormatException();
    }

    // Takes an api request as a String and returns the resulting response (XML/JSON) as a
    // String. Returns null if an exception is thrown
    public static String RequestToString(String request) {

        String response = "";

        try {
            BufferedReader reader = null;
            URL url = new URL(request);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];

            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            response = buffer.toString();

            if (reader != null) {
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return response;
    }

    // Takes an HTTPServletRequest and returns the IP (if obtainable) as a String
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_VIA");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
