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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author slukjanov aka Frostman
 */
public class DTOMapperTest {
    @Test
    public void initializationTest() throws Exception {
        DTOMapper dtoMapper = new DTOMapper();
        assertNotNull(dtoMapper);
    }

    @Test
    public void mappingTest() throws Exception {
        DTOMapper dtoMapper = new DTOMapper();
        dtoMapper.map(User.class);
    }

    @Test
    public void simpleDTOTest() throws Exception {
        DTOMapper dtoMapper = new DTOMapper();
        dtoMapper.map(User.class);

        User u1 = new User();
        String u1OidId = "u1-oid";
        ObjectId u1Oid = new ObjectId(u1OidId);
        u1.setId(u1Oid);
        String u1Login = "u1-login";
        u1.setLogin(u1Login);
        String u1Password = "u1-password";
        u1.setPassword(u1Password);

        UserDTO dto = (UserDTO) dtoMapper.toDTO(u1);

        assertEquals(u1OidId, dto.getId());
        assertEquals(u1Login, dto.getLogin());
        assertEquals(u1Password, dto.getPswd());
        assertNull(dto.getUser());

        User u1t = (User) dtoMapper.fromDTO(dto);

        assertEquals(u1Oid, u1t.getId());
        assertEquals(u1Login, u1t.getLogin());
        assertEquals(u1Password, u1t.getPassword());
        assertNull(u1t.getUser());
    }

    @Test
    public void cyclicReferencesTest() throws Exception {

    }
}
