package com.example.pokedexwithoutjparepo.controller;

import com.example.pokedexwithoutjparepo.Repo.PokemonRepo;
import com.example.pokedexwithoutjparepo.model.Pokemon;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    PokemonRepo pokemonRepo;

    public HomeController(PokemonRepo pokemonRepo) {
        this.pokemonRepo = pokemonRepo;
    }

    @RequestMapping("/")
    public String index(Model model) {

        return viewPage(model);
    }

    @RequestMapping("/sorted")
    public String viewPage(Model model) {

        List<Pokemon> sort = pokemonRepo.getAll();

        model.addAttribute("pokemons", sort);
        return "index";

    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Pokemon pokemon = pokemonRepo.findById(id);

        model.addAttribute("pokemon", pokemon);
        return "update-pokemon";
    }

    @RequestMapping("/{id}")
    public String pokemonIndividual(@PathVariable("id") long id, Model model) {
        Pokemon pokemon = pokemonRepo.findById(id);

        model.addAttribute("pokemons", pokemon);
        return "pokemonindividual";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Pokemon pokemon) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(Pokemon pokemon, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        pokemonRepo.addPokemon(pokemon);
        return "redirect:/";

    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, Pokemon pokemon,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            pokemon.setId(id);
            return "update-pokemon";
        }

        pokemonRepo.updatePokemon(pokemon);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
       // Pokemon pokemon = pokemonRepo.findById(id);
        pokemonRepo.deleteById(id);
        return "redirect:/";
    }
}