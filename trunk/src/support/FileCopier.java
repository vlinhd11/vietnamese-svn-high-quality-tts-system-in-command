package support;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lelightwin
 */
public class FileCopier {

    public static void run(String source, String destination) {
        try {
            // Create channel on the source
            FileChannel srcChannel = new FileInputStream(source).getChannel();

            // Create channel on the destination
            FileChannel dstChannel = new FileOutputStream(destination).getChannel();

            // Copy file contents from source to destination
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

            // Close the channels
            srcChannel.close();
            dstChannel.close();
        } catch (IOException e) {
        }
    }
    
    public static void main(String[] args) {
        FileCopier.run("E:/test.wav", "E:/test2.wav");
    }
}
