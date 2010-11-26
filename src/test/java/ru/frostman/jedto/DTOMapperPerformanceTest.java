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

package ru.frostman.jedto;

import org.junit.Test;
import ru.frostman.jedto.testbeans.ObjectId;
import ru.frostman.jedto.testbeans.User;
import ru.frostman.jedto.testbeans.UserDTO;

import static org.junit.Assert.*;

/**
 * @author slukjanov aka Frostman
 */
public class DTOMapperPerformanceTest {
    private static final int NUMBER_OF_ITERATIONS = 1000000;

    @Test
    public void simpleDTOTest() throws Exception {
        DTOMapper dtoMapper = new DTOMapper();
        dtoMapper.map(User.class).prepare();

        long startTime, endTime, firstTestLen, secondTestLen;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            User u1 = new User();
            String u1OidId = "u1-oid";
            ObjectId u1Oid = new ObjectId(u1OidId);
            u1.setId(u1Oid);
            String u1Login = "u1-login";
            u1.setLogin(u1Login);
            String u1Password = "u1-password";
            u1.setPassword(u1Password);

            UserDTO dto = new UserDTO(u1);
            dto.getId();
            dto.getLogin();
            User u1t = new User(dto);
            u1t.getId();
            u1t.getLogin();
        }
        endTime = System.currentTimeMillis();
        firstTestLen = endTime - startTime;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            User u1 = new User();
            String u1OidId = "u1-oid";
            ObjectId u1Oid = new ObjectId(u1OidId);
            u1.setId(u1Oid);
            String u1Login = "u1-login";
            u1.setLogin(u1Login);
            String u1Password = "u1-password";
            u1.setPassword(u1Password);

            UserDTO dto = (UserDTO) dtoMapper.toDTO(u1);
            dto.getId();
            dto.getLogin();
            User u1t = (User) dtoMapper.fromDTO(dto);
            u1t.getId();
            u1t.getLogin();
        }
        endTime = System.currentTimeMillis();
        secondTestLen = endTime - startTime;

        assertTrue(20 * firstTestLen > secondTestLen);
    }
}
