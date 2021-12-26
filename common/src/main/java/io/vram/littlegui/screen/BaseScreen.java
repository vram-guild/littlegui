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
import java.util.function.IntConsumer;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import io.vram.littlegui.widget.CycleButton;
import io.vram.littlegui.widget.Label;
import io.vram.littlegui.widget.SavingWidget;
import io.vram.littlegui.widget.Slider;
import io.vram.littlegui.widget.Toggle;

public abstract class BaseScreen<T> extends Screen {
	protected final Screen parent;
	public final T data;
	public final ComponentSource componentSource;
	protected final ObjectArrayList<Label> labels = new ObjectArrayList<>();

	protected int controlWidth;
	protected int controlHeight;
	protected int padding;
	protected int lineHeight;
	protected int leftOffset;
	protected int middleOffset;
	protected int rightOffset;
	protected int fullControlWidth;
	protected int halfControlWidth;
	protected int rightMargin;
	protected int halfOffset;

	public Font font() {
		return this.font;
	}

	protected void initSizes() {
		controlWidth = Mth.clamp((width - 16) / 3, 120, 200);
		controlHeight = Mth.clamp(height / 12, 16, 20);
		padding = Mth.clamp(Math.min((width - controlWidth * 3) / 4, (height - controlHeight * 8) / 8), 2, 10);
		lineHeight = controlHeight + padding;
		leftOffset = width / 2 - controlWidth - padding - controlWidth / 2;
		middleOffset = width / 2 - controlWidth / 2;
		rightOffset = width / 2 + controlWidth / 2 + padding;
		rightMargin = rightOffset + controlWidth;
		fullControlWidth = rightMargin - leftOffset;
		halfControlWidth = (fullControlWidth - padding) / 2;
		halfOffset = rightMargin - halfControlWidth;
	}

	public BaseScreen(Component title, Screen parent, T data, ComponentSource componentSource) {
		super(title);
		this.parent = parent;
		this.data = data;
		this.componentSource = componentSource;
	}

	protected void saveValues() {
		updateDataFromWidgets();
		saveData();
	}

	protected void updateDataFromWidgets() {
		for (final GuiEventListener w : this.children()) {
			if (w instanceof SavingWidget) {
				((SavingWidget) w).save();
			}
		}
	}

	protected abstract void saveData();

	@Override
	public void removed() {
		// NOOP
	}

	@Override
	public void onClose() {
		minecraft.setScreen(parent);
	}

	@Override
	protected void init() {
		initSizes();
		labels.clear();
		clearWidgets();

		labels.add(new Label(this, title, width / 2, padding));

		addControls();
	}

	protected abstract void addControls();

	@Override
	public void render(PoseStack matrixStack, int i, int j, float f) {
		renderDirtBackground(i);

		for (final var l : labels) {
			l.render(matrixStack);
		}

		super.render(matrixStack, i, j, f);
	}

	@SuppressWarnings("unchecked")
	protected <S extends BaseScreen<T>> Toggle<S> addToggle(
		HorizontalLayout horizontalLayout,
		VerticalLayout verticalLayout,
		String baseKey,
		boolean value,
		BooleanConsumer saveFunc
	) {
		return addRenderableWidget(new Toggle<>((S) this, horizontalLayout.left(this), verticalLayout.top(this), horizontalLayout.width(this), verticalLayout.height(this), baseKey, value, saveFunc));
	}

	@SuppressWarnings("unchecked")
	protected <S extends BaseScreen<T>, V extends Enum<?>> CycleButton<V, S> addCycleButton(
		HorizontalLayout horizontalLayout,
		VerticalLayout verticalLayout,
		String baseKey,
		Class<V> e,
		V value,
		Consumer<V> saveFunc
	) {
		return addRenderableWidget(new CycleButton<>((S) this, horizontalLayout.left(this), verticalLayout.top(this), horizontalLayout.width(this), verticalLayout.height(this), baseKey, e, value, saveFunc));
	}

	@SuppressWarnings("unchecked")
	protected <S extends BaseScreen<T>> Slider<S> addSlider(
		HorizontalLayout horizontalLayout,
		VerticalLayout verticalLayout,
		String baseKey,
		int min,
		int max,
		int value,
		IntConsumer saveFunc
	) {
		return addRenderableWidget(new Slider<>((S) this, horizontalLayout.left(this), verticalLayout.top(this), horizontalLayout.width(this), verticalLayout.height(this), baseKey, min, max, value, saveFunc));
	}
}
