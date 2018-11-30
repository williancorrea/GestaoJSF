/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.atlantis.view;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named
@SessionScoped
public class GuestPreferences implements Serializable {

    @Getter
    private Map<String, String> themeColors;

    @Getter
    @Setter
    private String theme = "blue";

    @Getter
    @Setter
    private String layout = "dark";

    @Getter
    @Setter
    private boolean overlayMenu;

    @Getter
    @Setter
    private boolean darkMenu;

    @Getter
    @Setter
    private boolean orientationRTL;

    @PostConstruct
    public void init() {
        themeColors = new HashMap<String, String>();
        themeColors.put("turquoise", "#47c5d4");
        themeColors.put("blue", "#3192e1");
        themeColors.put("orange", "#ff9c59");
        themeColors.put("pink", "#e42a7b");
        themeColors.put("purple", "#985edb");
        themeColors.put("green", "#5ea980");
        themeColors.put("black", "#545b61");
    }
}
