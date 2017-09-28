/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hofman;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author step
 */
public class Hofman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FileOutputStream fos = null;
        try {
            // TODO code application logic here
            Tree t = new Tree("Primer hofmanovog stabla");
            BitSet compBin = t.compress();
            fos = new FileOutputStream("compressedData");
            fos.write(compBin.toByteArray());
            fos.flush();
            fos.close();
            DataInputStream dis = new DataInputStream(new FileInputStream("compressedData"));
            byte b;
            ArrayList<Byte> bytes = new ArrayList<>();
            while(true) {
                try {
                b=dis.readByte();
                bytes.add(b);
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println(t.decompress(bytes));
            dis.close();
            //t.printPreOrder();
            //t.printTree();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Hofman.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Hofman.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(Hofman.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
