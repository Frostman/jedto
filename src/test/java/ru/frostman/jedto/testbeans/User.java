/*
 * Copyright 2010 Sergey "Frostman" Lukjanov
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

package ru.frostman.jedto.testbeans;

import ru.frostman.jedto.annotations.MapDTO;
import ru.frostman.jedto.annotations.MapTo;
import ru.frostman.jedto.annotations.Transform;

/**
 * @author slukjanov aka Frostman
 */
@MapDTO(UserDTO.class)
public class User {
    @Transform(ObjectIdTransformer.class)
    private ObjectId id;

    private String login;

    @MapTo("pswd")
    private String password;

    private User user;

    public User() {
    }

    public User(UserDTO userDTO) {
        this.id = new ObjectId(userDTO.getId());
        this.login = userDTO.getLogin();
        this.password = userDTO.getPswd();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
