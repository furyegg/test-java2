package com.lombardrisk.ocelot.efc.component.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;

public class ImageViewNew extends ImageView {

	public Image valueOf(URL url) throws IOException {
		return new Image(url.openStream());
	}

}
