package com.example.pokedexwithoutjparepo.Repo;

import com.example.pokedexwithoutjparepo.model.Pokemon;
import com.example.pokedexwithoutjparepo.utility.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PokemonRepo {

    public List<Pokemon> getAll() {
        List<Pokemon> pokemonList = new ArrayList<>();

        Connection connection = ConnectionManager.getConnection();

        try {
            Statement statement = connection.createStatement();
            final String SQL_QUERY = "SELECT * FROM pokemon";
            ResultSet rs = statement.executeQuery(SQL_QUERY);

            while (rs.next()) {

                long id = rs.getLong(1);
                String name = rs.getString(2);
                int speed = rs.getInt(3);
                int specialDefence = rs.getInt(4);
                int specialAttack = rs.getInt(5);
                int defence = rs.getInt(6);
                int attack = rs.getInt(7);
                int hp = rs.getInt(8);
                String primaryType = rs.getString(9);
                String secondaryType = rs.getString(10);

                pokemonList.add(new Pokemon(id, name, speed, specialDefence, specialAttack, defence, attack, hp, primaryType, secondaryType));

            }

            statement.close();


        } catch (SQLException e) {
            System.out.println("Could not create connection");
            e.printStackTrace();
        }

        return pokemonList;
    }

    public Pokemon findById(long id) {

        Connection connection = ConnectionManager.getConnection();

        try {

            Statement statement = connection.createStatement();
            final String SQL_QUERY = "SELECT * FROM pokemon WHERE id = ?";

            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();
            {

                long pId = rs.getLong(1);
                String pName = rs.getString(2);
                int speed = rs.getInt(3);
                int specialDefence = rs.getInt(4);
                int specialAttack = rs.getInt(5);
                int defence = rs.getInt(6);
                int attack = rs.getInt(7);
                int hp = rs.getInt(8);
                String primaryType = rs.getString(9);
                String secondaryType = rs.getString(10);

                return new Pokemon(pId, pName, speed, specialDefence, specialAttack, defence, attack, hp, primaryType, secondaryType);

            }

        } catch (SQLException e) {
            System.out.println("Could not create Connection");
            e.printStackTrace();
        }
        return null;


    }

    public void addPokemon(Pokemon pokemon) {

        Connection connection = ConnectionManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO pokemon(name,speed,special_defence,special_attack,defence,attack,hp,primary_type,secondary_type) VALUES (?,?,?,?,?,?,?,?,?) ");
            preparedStatement.setString(1, pokemon.getName());
            preparedStatement.setInt(2, pokemon.getSpeed());
            preparedStatement.setInt(3, pokemon.getSpecialDefence());
            preparedStatement.setInt(4, pokemon.getSpecialAttack());
            preparedStatement.setInt(5, pokemon.getDefence());
            preparedStatement.setInt(6, pokemon.getAttack());
            preparedStatement.setInt(7, pokemon.getHp());
            preparedStatement.setString(8, pokemon.getPrimaryType());
            preparedStatement.setString(9, pokemon.getSecondaryType());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Could not create Connection");
            e.printStackTrace();
        }

    }

    public void updatePokemon(Pokemon pokemon) {

        final String UPDATE_QUERY = "UPDATE pokemon SET name = ?, speed = ?, special_defence = ?, special_attack = ?," +
                " defence = ?, attack = ?, hp = ?, primary_type = ?, secondary_type = ? WHERE id = ? ";

        long id = pokemon.getId();
        String name = pokemon.getName();
        int speed = pokemon.getSpeed();
        int specialDefence = pokemon.getSpecialDefence();
        int specialAttack = pokemon.getSpecialAttack();
        int defence = pokemon.getDefence();
        int attack = pokemon.getAttack();
        int hp = pokemon.getHp();
        String primaryType = pokemon.getPrimaryType();
        String secondaryType = pokemon.getSecondaryType();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, speed);
            preparedStatement.setInt(3, specialDefence);
            preparedStatement.setInt(4, specialAttack);
            preparedStatement.setInt(5, defence);
            preparedStatement.setInt(6, attack);
            preparedStatement.setInt(7, hp);
            preparedStatement.setString(8, primaryType);
            preparedStatement.setString(9, secondaryType);
            preparedStatement.setLong(10, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not create connection");
            e.printStackTrace();
        }

    }

    public void deleteById(long id) {

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pokemon WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not create connection");
            e.printStackTrace();
        }

    }

}



