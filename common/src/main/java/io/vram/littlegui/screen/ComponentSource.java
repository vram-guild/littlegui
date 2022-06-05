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

import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.network.chat.Component;

public record ComponentSource(Function<String, Component> labelSource, Function<String, Component> toolTipSource, BiFunction<String, String, Component> subLabelSource) {
	public static ComponentSource of(String labelPrefix, String tooltipPrefix) {
		return new ComponentSource(
				str -> Component.translatable(labelPrefix + str),
				str -> Component.translatable(tooltipPrefix + str),
				(str, val) -> Component.translatable(labelPrefix + str + "." + val));
	}
}
