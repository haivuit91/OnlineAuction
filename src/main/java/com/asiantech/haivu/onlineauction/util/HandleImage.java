package com.asiantech.haivu.onlineauction.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class HandleImage {

	public static final int DEFAULT_BUFFER_SIZE = 10240;

	public String getPathImg(String urlImage) {
		return Constants.IMAGE_DOCROOT + File.separator + urlImage;
	}

	public File getFileImage(String urlImage) throws IOException {
		String pathImage = getPathImg(urlImage);
		File file = new File(pathImage);
		if (!file.exists()) {
			return null;
		}
		return file;
	}

	public boolean uploadFileHandler(MultipartFile file) {
		boolean check = false;
		if (!file.isEmpty()) {
			String name = file.getOriginalFilename();
			try {
				File dir = new File(Constants.IMAGE_DOCROOT);
				if (!dir.exists())
					dir.mkdirs();
				BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
				int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
						: originalImage.getType();
				BufferedImage resizeImageJpg = resizeImage(originalImage, type);
				ImageIO.write(resizeImageJpg, "jpg",
						new File(dir.getAbsolutePath() + File.separator + name));
				check = true;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return check;
	}

	private static BufferedImage resizeImage(BufferedImage originalImage,
			int type) {
		BufferedImage resizedImage = new BufferedImage(Constants.IMG_WIDTH,
				Constants.IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, Constants.IMG_WIDTH,
				Constants.IMG_HEIGHT, null);
		g.dispose();
		return resizedImage;
	}

}
