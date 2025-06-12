package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Adresse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdresseDaoImplTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    private AdresseDaoImpl adresseDao;
    private Adresse adresse;

    @BeforeEach
    void setUp() {
        adresseDao = new AdresseDaoImpl(jdbcTemplate);

        adresse = new Adresse();
        adresse.setId(1L);
        adresse.setRue("123 Rue Test");
        adresse.setCodePostal("75001");
        adresse.setVille("Paris");
    }

    @Test
    void testCreate() {
        // Mock du KeyHolder pour retourner un ID généré
        when(jdbcTemplate.update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class)))
                .thenAnswer(invocation -> {
                    KeyHolder keyHolder = invocation.getArgument(2);
                    keyHolder.getKeyList().add(Map.of("GENERATED_KEY", 1L));
                    return 1;
                });

        long result = adresseDao.create(adresse);

        assertEquals(1L, result);
        verify(jdbcTemplate).update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class));
    }

    @Test
    void testRead() {
        when(jdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), any(AdresseRowMapper.class)))
                .thenReturn(adresse);

        Adresse result = adresseDao.read(1L);

        assertNotNull(result);
        assertEquals(adresse.getId(), result.getId());
        verify(jdbcTemplate).queryForObject(anyString(), any(MapSqlParameterSource.class), any(AdresseRowMapper.class));
    }

    @Test
    void testReadAll() {
        when(jdbcTemplate.query(anyString(), any(AdresseRowMapper.class)))
                .thenReturn(List.of(adresse));

        List<Adresse> result = adresseDao.readAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(jdbcTemplate).query(anyString(), any(AdresseRowMapper.class));
    }

    @Test
    void testUpdate() {
        when(jdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                .thenReturn(1);

        assertDoesNotThrow(() -> adresseDao.update(adresse));
        verify(jdbcTemplate).update(anyString(), any(MapSqlParameterSource.class));
    }

    @Test
    void testDelete() {
        when(jdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                .thenReturn(1);

        assertDoesNotThrow(() -> adresseDao.delete(1L));
        verify(jdbcTemplate).update(anyString(), any(MapSqlParameterSource.class));
    }
}