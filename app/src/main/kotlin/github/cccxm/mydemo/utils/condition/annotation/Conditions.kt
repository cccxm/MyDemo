package github.cccxm.mydemo.utils.condition.annotation

/**
 *  Copyright (C) <2017>  <陈小默>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Created by 陈小默 on 5/07.
 */
enum class Conditions {
    /**
     * 任何情况下，带有此标记的方法全部被触发才可能触发目标方法。
     */
    AND,
    /**
     * 任何情况下，带有此标记的方法至少被触发一个才可能触发目标方法。
     */
    OR,
    /**
     * 任何情况下，带有此标记的方法至少被触发一次则目标方法不会被触发。
     */
    NOT
}
