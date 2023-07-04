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

package io.vram.littlegui.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import io.vram.littlegui.screen.BaseScreen;

public class Label {
	protected final BaseScreen<?> parentScreen;
	protected final Component label;
	protected final int center;
	protected final int top;

	public Label(BaseScreen<?> configScreen, Component label, int center, int top) {
		this.parentScreen = configScreen;
		this.label = label;
		this.center = center;
		this.top = top;
	}

	public void render(GuiGraphics guiGraphics) {
		guiGraphics.drawCenteredString(parentScreen.font(), label, center, top, 16777215);
	}
}
