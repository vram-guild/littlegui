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
import java.util.function.IntConsumer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import io.vram.littlegui.screen.BaseScreen;

public class Slider<S extends BaseScreen<?>> extends AbstractSliderButton implements SavingWidget {
	protected final S parentScreen;
	protected final int min;
	protected final int max;
	protected int intValue;
	protected final String baseLabel;
	protected final List<FormattedCharSequence> toolTip;
	protected final IntConsumer saveFunc;

	public Slider(S parentScreen, int left, int top, int width, int height, String baseKey, int min, int max, int value, IntConsumer saveFunc) {
		super(left, top, width, height, parentScreen.componentSource.labelSource().apply(baseKey), normalize(min, max, value));
		this.parentScreen = parentScreen;
		this.saveFunc = saveFunc;
		toolTip = parentScreen.font().split(parentScreen.componentSource.toolTipSource().apply(baseKey), 200);
		this.intValue = value;
		this.min = min;
		this.max = max;
		baseLabel = parentScreen.componentSource.labelSource().apply(baseKey) + ": ";
		updateMessage();
	}

	@Override
	protected void updateMessage() {
		this.setMessage(new TextComponent(baseLabel + intValue));
	}

	@Override
	protected void applyValue() {
		intValue = (int) Mth.lerp(Mth.clamp(this.value, 0.0, 1.0), min, max);
	}

	protected int getValue() {
		return intValue;
	}

	protected void setValue(int value) {
		this.intValue = value;
		this.value = normalize(min, max, value);
		updateMessage();
	}

	protected static double normalize(int min, int max, int value) {
		value = Mth.clamp(value, min, max);
		return (value - min) / (double) (max - min + 1);
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
		saveFunc.accept(intValue);
	}
}
