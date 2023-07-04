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

import java.util.List;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;

import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.util.FormattedCharSequence;

import io.vram.littlegui.screen.BaseScreen;

public class Toggle<S extends BaseScreen<?>> extends Checkbox implements SavingWidget {
	protected final S parentScreen;
	protected final List<FormattedCharSequence> toolTip;
	protected final BooleanConsumer saveFunc;

	public Toggle(S parentScreen, int left, int top, int width, int height, String baseKey, boolean value, BooleanConsumer saveFunc) {
		super(left, top, width, height, parentScreen.componentSource.labelSource().apply(baseKey), value);
		this.parentScreen = parentScreen;
		this.saveFunc = saveFunc;
		toolTip = parentScreen.font().split(parentScreen.componentSource.toolTipSource().apply(baseKey), 200);
	}

	@Override
	public void save() {
		saveFunc.accept(selected());
	}
}
