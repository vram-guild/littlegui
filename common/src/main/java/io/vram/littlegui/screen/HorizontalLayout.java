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

import java.util.function.ToIntFunction;

public enum HorizontalLayout {
	FULL(s -> s.leftOffset, s -> s.fullControlWidth),
	LEFT_HALF(s -> s.leftOffset, s -> s.halfControlWidth),
	RIGHT_HALF(s -> s.halfOffset, s -> s.halfControlWidth),
	LEFT_THIRD(s -> s.leftOffset, s -> s.controlWidth),
	MIDDLE_THIRD(s -> s.middleOffset, s -> s.controlWidth),
	RIGHT_THIRD(s -> s.rightOffset, s -> s.controlWidth);

	private final ToIntFunction<BaseScreen<?>> leftFunc;
	private final ToIntFunction<BaseScreen<?>> widthFunc;

	public int left(BaseScreen<?> forScreen) {
		return leftFunc.applyAsInt(forScreen);
	}

	public int width(BaseScreen<?> forScreen) {
		return widthFunc.applyAsInt(forScreen);
	}

	HorizontalLayout(ToIntFunction<BaseScreen<?>> leftFunc, ToIntFunction<BaseScreen<?>> widthFunc) {
		this.leftFunc = leftFunc;
		this.widthFunc = widthFunc;
	}
}
