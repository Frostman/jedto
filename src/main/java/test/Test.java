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

package test;

import ru.frostman.jedto.DTOMapper;

/**
 * @author slukjanov aka Frostman
 */
public class Test {
    public static void main(String[] args) {
        DTOMapper dtoMapper = new DTOMapper();
        dtoMapper.map(User.class);

        dtoMapper.prepare();

        User user =  new User();
        user.setLogin("logggin");
        user.setPassword("passwordddd");
        user.setId(new ObjectId("objId"));
        User u2 = new User();
        u2.setLogin("uuuuu2");
        u2.setPassword("ppppp2");
        user.setUser(u2);
        u2.setUser(user);

        UserDTO userDTO = (UserDTO) dtoMapper.toDTO(user);
        System.out.println(userDTO);

        User ttt = (User) dtoMapper.fromDTO(userDTO);
        System.out.println(ttt);

    }
}
