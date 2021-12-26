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
import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import io.vram.littlegui.screen.BaseScreen;

public class CycleButton<V extends Enum<?>, S extends BaseScreen<?>> extends AbstractButton implements SavingWidget {
	private final S parentScreen;
	protected final List<FormattedCharSequence> toolTip;
	protected final V[] values;
	protected final Component[] valueLabels;
	protected final Consumer<V> saveFunc;
	protected V value;

	public CycleButton(S parentScreen, int left, int top, int width, int height, String baseKey, Class<V> e, V value, Consumer<V> saveFunc) {
		super(left, top, width, height, parentScreen.componentSource.labelSource().apply(baseKey));
		this.parentScreen = parentScreen;
		this.saveFunc = saveFunc;
		this.values = e.getEnumConstants();
		this.value = value;
		this.valueLabels = new Component[values.length];

		for (int i = 0; i < values.length; ++i) {
			valueLabels[i] = parentScreen.componentSource.subLabelSource().apply(baseKey, values[i].name().toLowerCase());
		}

		toolTip = parentScreen.font().split(parentScreen.componentSource.toolTipSource().apply(baseKey), 200);
		setMessage(valueLabels[value.ordinal()]);
	}

	@Override
	public void updateNarration(NarrationElementOutput var1) {
		// TODO implement?
	}

	@Override
	public void onPress() {
		if (Screen.hasShiftDown()) {
			this.cycleValue(-1);
		} else {
			this.cycleValue(1);
		}
	}

	protected void cycleValue(int i) {
		final int index = Mth.positiveModulo(value.ordinal() + i, values.length);
		value = values[index];
		setMessage(valueLabels[index]);
	}

	protected V getValue() {
		return value;
	}

	protected void setValue(V value) {
		this.value = value;
		setMessage(valueLabels[value.ordinal()]);
	}

	@Override
	public void renderButton(PoseStack matrices, int mouseX, int mouseY, float delta) {
		super.renderButton(matrices, mouseX, mouseY, delta);

		if (isHovered) {
			parentScreen.renderTooltip(matrices, toolTip, mouseX, mouseY);
		}
	}

	@Override
	public void save() {
		saveFunc.accept(value);
	}
}
