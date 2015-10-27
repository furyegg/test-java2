package com.lombardrisk.ocelot.efc.controller;

import com.lombardrisk.ocelot.efc.component.pane.BlockScalePane;
import com.lombardrisk.ocelot.efc.component.pane.HScalePane;
import com.lombardrisk.ocelot.efc.component.pane.SwitchPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class SwitchController {

	@FXML
	public SwitchPane swithPane;

	@FXML
	public HScalePane hScalePane;

	@FXML
	public BlockScalePane blockScalePane;

	@FXML
	private ImageView img1;

	@FXML
	private ImageView img2;

	public void handleSwith(ActionEvent actionEvent) {
		if (swithPane.isFirstShowed()) {
			swithPane.hideFirst();
			swithPane.showSecond();
		} else {
			swithPane.showFirst();
			swithPane.hideSecond();
		}
	}

	public void showLeft(ActionEvent actionEvent) {
		hScalePane.activeLeft();
	}

	public void showRight(ActionEvent actionEvent) {
		hScalePane.activeRight();
	}

	public void showFirst(ActionEvent actionEvent) {
		blockScalePane.activeFirst();
	}

	public void showSecond(ActionEvent actionEvent) {
		blockScalePane.activeSecond();
	}

	public void showThird(ActionEvent actionEvent) {
		blockScalePane.activeThird();
	}

}