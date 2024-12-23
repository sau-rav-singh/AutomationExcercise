package others;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFTest {

    private static List<File> getListOfFiles(String directoryName) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        assert fList != null;
        return List.of(fList);
    }

    @Test
    public void pdfTest() {
        String downloadPath = System.getProperty("user.home") + "/Downloads/";
        List<File> files = getListOfFiles(downloadPath);
        File downloadedFile = null;
        for (File file : files) {
            if (file.getName().endsWith(".pdf")) {
                downloadedFile = file;
                break;
            }
        }
        if (downloadedFile != null) {
            try {
                PDDocument document = PDDocument.load(downloadedFile);
                PDFTextStripper stripper = new PDFTextStripper();
                String pdfText = stripper.getText(document);
                if (pdfText.contains("EPAM")) {
                    System.out.println("PDF downloaded and validation passed.");
                } else {
                    System.out.println("PDF downloaded but validation failed. Expected text not found.");
                }
                document.close();
            } catch (IOException e) {
                System.err.println("Error reading PDF: " + e.getMessage());
            }
            if (downloadedFile.delete()) {
                System.out.println("Downloaded file deleted.");
            } else {
                System.err.println("Failed to delete downloaded file.");
            }
        } else {
            System.out.println("Downloaded PDF not found.");
        }
    }
}
