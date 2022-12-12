/*
 * This file is part of Little GUI and is licensed to the project under
 * terms that are compatible with the GNU Lesser General Public License.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership and licensing.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.vram.littlegui.screen;

import java.util.function.Consumer;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public abstract class SaveCancelScreen<T> extends BaseScreen<T> {
	protected Consumer<T> saveFunction = d -> { };
	protected final Component saveLabel;
	protected final Component cancelLabel;

	public SaveCancelScreen(Component title, Screen parent, T data, ComponentSource componentSource, Component saveLabel, Component cancelLabel) {
		super(title, parent, data, componentSource);
		this.saveLabel = saveLabel;
		this.cancelLabel = cancelLabel;
	}

	public SaveCancelScreen(Component title, Screen parent, T data, ComponentSource componentSource) {
		this(title, parent, data, componentSource, CommonComponents.GUI_DONE, CommonComponents.GUI_CANCEL);
	}

	@Override
	protected void init() {
		super.init();

		addRenderableWidget(Button.builder(cancelLabel, (buttonWidget) -> {
			minecraft.setScreen(parent);
		}).bounds(width / 2 - 120 - padding / 2, height - lineHeight, 120, controlHeight).build());

		addRenderableWidget(Button.builder(saveLabel, (buttonWidget) -> {
			saveValues();
			minecraft.setScreen(parent);
		}).bounds(width / 2 + padding / 2, height - lineHeight, 120, controlHeight).build());
	}
}
