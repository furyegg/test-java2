package com.lombardrisk.ocelot.efc.component.pane;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;

public class SwitchPane extends StackPane {

	private static final Duration TRANS_DURATION = Duration.millis(2000);

	private Node first;
	private Node second;

	private boolean isShowFirst = true;

	@Override
	protected void layoutChildren() {
		super.layoutChildren();

		List<Node> children = getManagedChildren();
		if (children.size() != 2) {
			throw new RuntimeException("Switch box can only and must contains two children.");
		}

		first = children.get(0);
		first.setVisible(isShowFirst);
		second = children.get(1);
		second.setVisible(!isShowFirst);
	}

	public void showFirst() {
		first.setVisible(true);
		isShowFirst = true;
		getShowTransition(first).play();
	}

	public void hideFirst() {
		getHideTransition(first).play();
		first.setVisible(false);
		isShowFirst = false;
	}

	public void showSecond() {
		second.setVisible(true);
		isShowFirst = false;
		getShowTransition(second).play();
	}

	public void hideSecond() {
		getHideTransition(second).play();
		second.setVisible(false);
		isShowFirst = true;
	}

	public boolean isFirstShowed() {
		return first.isVisible();
	}

	private FadeTransition getShowTransition(Node node) {
		FadeTransition t = new FadeTransition();
		t.setDuration(TRANS_DURATION);
		t.setFromValue(0);
		t.setToValue(1);
		t.setNode(node);
		return t;
	}

	private FadeTransition getHideTransition(Node node) {
		FadeTransition t = new FadeTransition();
		t.setDuration(TRANS_DURATION);
		t.setFromValue(1);
		t.setToValue(0);
		t.setNode(node);
		return t;
	}

}