/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.apps.muzei.gallery.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Converts a {@link Date} into and from a persisted value
 */
public class DateTypeConverter {
    @TypeConverter
    public static Date fromTimestamp(Long timestamp) {
        return timestamp == null || timestamp == 0L ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null || date.getTime() == 0 ? null : date.getTime();
    }
}
