package com.servustech.skeleton.utils.filestorage;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.sql.Timestamp;


@Slf4j
public class FSFileStorage implements FileStorage {

    @Value("${spring.file-service.path}")
    private String path;

    @Value("${spring.file-service.domain}")
    private String domain;

    @Override
    public String store(MultipartFile file, String uri) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        uri = timestamp.getTime() + "_" + uri;

        File dest = new File(path + uri); //todo should we use relative path? will allow migrating fs folder

        log.debug("Destination for file: " + dest.getAbsolutePath());

        File parentFolder = dest.getParentFile();

        log.debug("Parent folder for file: " + parentFolder.getAbsolutePath());
        if (!parentFolder.exists()) {
            parentFolder.mkdirs();
        }

        Files.copy(file.getInputStream(), Paths.get(dest.getAbsolutePath()));

        return uri;
    }

    @Override
    public InputStream retrieve(String key) throws FileStorageException {
        File file = new File(path + key);
        if (file.exists()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new FileStorageException("Unable to create FileInputStream for " + key, e);
            }
        } else {
            throw new FileStorageException("Invalid key provided: " + key);
        }
    }

    @Override
    public boolean delete(String key) throws FileStorageException {
        File file = new File(path + key);
        if (file.exists()) {
            return file.delete();
        } else {
            // An exception is not thrown because we still need the entities to be deleted, even if the file cannot be found.
            // throw new FileStorageException("File not found", "file.not.found");
            return false;
        }
    }
    @Override
    public String generateTemporaryDownloadUrl(String key, String filename) {
        return localFileDownload(domain, filename, key);
    }

    public static String localFileDownload(String domain, String filename, String key) {
        return domain + "api/files/download?filename=" + filename + "&key=" + key;
    }

    @Override
    public String uploadCsvForDownloadUrl(StringWriter wr, String fileType) {
        //name the file unique in order to delete the report after downloading it
        SecureRandom random = new SecureRandom();
        String fileId = new BigInteger(130, random).toString(32);
        String key = fileType + "_" + fileId + ".csv";

        File parentFolder = new File(path + File.separator + File.separator);
        log.debug("Parent folder for file: " + parentFolder.getAbsolutePath());
        if (parentFolder != null && !parentFolder.exists()) {
            parentFolder.mkdirs();
        }

        File file = new File(parentFolder, key);
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(file));
            out.write(wr.toString());
        } catch (IOException e) {
            throw new FileStorageException("Error saving csv with key: " + key, "file.save.fail", key);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                throw new FileStorageException("Error saving csv with key: " + key, "file.save.fail", key);
            }
        }
        return file.getAbsolutePath();
    }

    @Override
    public String uploadCsvForDownloadUrl(byte[] payroll, String fileType) {
        //name the file unique in order to delete the report after downloading it
        SecureRandom random = new SecureRandom();
        String fileId = new BigInteger(130, random).toString(32);
        String key = fileType + "_" + fileId + ".csv";

        File parentFolder = new File(path + File.separator + File.separator);
        log.debug("Parent folder for file: " + parentFolder.getAbsolutePath());
        if (parentFolder != null && !parentFolder.exists()) {
            parentFolder.mkdirs();
        }

        File file = new File(parentFolder, key);
        try {
            FileUtils.writeByteArrayToFile(file, payroll);
        } catch (IOException e) {
            throw new FileStorageException("Error saving csv with key: " + key, "file.save.fail", key);
        }
        return file.getAbsolutePath();
    }


}
