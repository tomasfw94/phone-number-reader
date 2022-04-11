package com.spirent;

import com.spirent.model.PhoneNumber;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Main {

    public static final int FULL_PHONE_NUMBER_LENGTH = 11;
    public static final int PHONE_NUMBER_LENGTH_WITHOUT_COUNTRYCODE = 10;
    public static final int PHONE_NUMBER_LENGTH_WITHOUT_COUNTRYCODE_AND_CITYCODE = 7;

    public static void main(String[] args) throws IOException {
        File directory = new File("C:/path/to/root/directory");
        Set<PhoneNumber> phoneNumbers = readPhoneNumbersFromDirectoryFiles(directory.listFiles());
        phoneNumbers.stream().filter(Objects::nonNull).map(phoneNumber -> phoneNumber.getFormattedFullNumber()).distinct().sorted().forEach(System.out::println);
    }

    private static Set<PhoneNumber> readPhoneNumbersFromDirectoryFiles(File[] listOfFiles) throws IOException {
        Set<PhoneNumber> phoneNumbers = new HashSet<>();

        for (File file : listOfFiles) {
            if(file.isDirectory()){
                phoneNumbers.addAll(readPhoneNumbersFromDirectoryFiles(file.listFiles()));
            }

            if (file.isFile() && file.getName().endsWith(".txt")) {
                String fileContent = FileUtils.readFileToString(file, Charset.defaultCharset());
                String normalizedFileContent = fileContent.replaceAll("\\s+","").replaceAll("\\+", "").replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "");
                phoneNumbers.addAll(readPhoneNumbersFromNormalizedFileContent(normalizedFileContent));
            }
        }
        return phoneNumbers;
    }

    private static Set<PhoneNumber> readPhoneNumbersFromNormalizedFileContent(String normalizedFileContent) {
        Set<PhoneNumber> readPhoneNumbers = new HashSet<>();
        String[] rawNumbers = normalizedFileContent.split(",");

        PhoneNumber phoneNumber = null;
        for (String rawNumber: rawNumbers) {
            if(rawNumber.length() == PHONE_NUMBER_LENGTH_WITHOUT_COUNTRYCODE_AND_CITYCODE){
                phoneNumber = new PhoneNumber(rawNumber);
            } else if(rawNumber.length() == PHONE_NUMBER_LENGTH_WITHOUT_COUNTRYCODE){
                phoneNumber = new PhoneNumber(rawNumber.substring(0, 3), rawNumber.substring(3));
            } else if(rawNumber.length() == FULL_PHONE_NUMBER_LENGTH){
                phoneNumber = new PhoneNumber(rawNumber.substring(0, 1), rawNumber.substring(1, 4), rawNumber.substring(4));
            }
            if(phoneNumber != null){
                readPhoneNumbers.add(phoneNumber);
            }
        }
        return readPhoneNumbers;
    }
}
