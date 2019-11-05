package pw.react.backend.reactbackend;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import pw.react.backend.reactbackend.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService service;

    @Mock
    private UserRepository repository;

    @Before
    public void setUp() throws Exception {
        service = spy(new UserService(repository));
    }

    @Test
    public void givenNotExistingUser_whenUpdateUser_thenThrowResourceNotFoundException() {
        User user = new User();
        user.setId(1);
        when(repository.findById(anyLong())).thenReturn(null);

        try {
            service.updateUser(user);
            fail("Should throw ResourceNotFoundException");
        } catch (RuntimeException ex) {
            assertThat(ex.getMessage(), is(equalTo("User with id [1] not found.")));
        }
        verify(repository, times(0)).save(any(User.class));
    }

    @Test
    public void givenExistingUser_whenUpdateUser_thenExecuteSaveMethod() {
        User user = new User();
        user.setId(1);
        when(repository.findById(anyLong())).thenReturn(user);

        service.updateUser(user);

        verify(repository, times(1)).save(eq((user)));
    }

    @Test
    public void givenExistingUser_whenUpdateUser_thenReturnUpdatedUser() {
        User updatedUser = new User();
        updatedUser.setId(1);
        //when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.save(any(User.class))).thenReturn(updatedUser);
        when(repository.findById(anyLong())).thenReturn(updatedUser);

        User actual = service.updateUser(updatedUser);

        assertThat(actual, is(updatedUser));
        verify(repository, times(1)).save(eq(updatedUser));
        //verify(repository, times(1)).existsById(eq(new Long(1)));
    }

    @Test
    public void givenNotExistingUserLogin_whenExists_thenReturnFalse() {
        User user = new User();
        user.setLogin("asd");
        when(repository.findById(anyLong())).thenReturn(null);

        boolean res = service.exists(user.getLogin());

        assertThat(res, is(false));
    }
}
