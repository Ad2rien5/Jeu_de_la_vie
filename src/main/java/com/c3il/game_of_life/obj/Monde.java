package com.c3il.game_of_life.obj;

import javafx.geometry.Pos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Monde {
    private final Integer longueur;
    private final Integer largeur;
    private Map<Position, Boolean> grille;

    public Monde(Integer longueur, Integer largeur) {
        this.longueur = longueur;
        this.largeur = largeur;

        List<Position> liste_record = IntStream.range(0, this.largeur)
                .boxed()
                .flatMap(y -> IntStream.range(0, this.longueur)
                        .mapToObj(x -> new Position(x, y))
                )
                .toList();

        this.grille = liste_record
                .stream()
                .collect(Collectors.toMap(
                        x -> x,
                        x -> false
                ));
    }

    public Boolean estVivant(Position p) {
        return this.grille.get(p);
    }

    public void changerEtat(Position p) {
        this.grille.put(p, !this.grille.get(p));
    }

    private long voisinsVivant(Position p) {
        return this.grille.keySet()
                .stream()
                .filter(x -> (
                        (List.of(p.x(), p.x()-1, p.x()+1).contains(x.x()) && List.of(p.y(), p.y()-1, p.y()+1).contains(x.y())) &&
                        (x != p) &&
                        (this.grille.get(x))
                ))
                .toArray().length;
    }

    private long voisinsVivant(Position p, Map<Position, Boolean> dico) {
        return dico.keySet()
                .stream()
                .filter(x -> (
                        (List.of(p.x(), p.x()-1, p.x()+1).contains(x.x()) && List.of(p.y(), p.y()-1, p.y()+1).contains(x.y())) &&
                                (x != p) &&
                                (dico.get(x))
                ))
                .toArray().length;
    }

    public void prochaineGeneration() {
        Map<Position, Boolean> copie = new HashMap<>(this.grille);
        copie.keySet()
                .stream()
                .filter( x -> (
                        (copie.get(x) && voisinsVivant(x, copie) != 2) ||
                        (!copie.get(x) && voisinsVivant(x, copie) == 3)
                ))
                .forEach(this::changerEtat);
    }

    public Map<Position, Boolean> getGrille() {
        return grille;
    }
}
