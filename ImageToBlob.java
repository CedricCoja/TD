package user;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.primefaces.model.UploadedFile;

/**
 * This class is a HelperClass to create a Image in the DB.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */

public class ImageToBlob {

	/**
	 * Creates a new Blob
	 * 
	 * @return Blob
	 */

	public static Blob toBlob(UploadedFile image) throws SerialException, SQLException {
		return toBlob(image.getContents());
	}

	/**
	 * Creates a new Blob
	 * 
	 * @return Blob
	 */
	public static Blob toBlob(byte[] image) throws SerialException, SQLException {
		return new SerialBlob(image);
	}

	/**
	 * Reads a BufferedImage from a InputStream
	 * 
	 * @return BufferedImage
	 */

	public static BufferedImage toImage(InputStream stream) {
		try {
			return ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}