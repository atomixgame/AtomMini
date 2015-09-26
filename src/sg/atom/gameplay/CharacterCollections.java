/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import com.google.common.base.Predicate;
import com.jme3.math.Vector3f;

/**
 *
 * @author cuongnguyen
 */
public class CharacterCollections {

    public static class GetByRankPredicate implements Predicate<CommonGameCharacter> {

        int rank;

        public GetByRankPredicate(int rank) {
            this.rank = rank;
        }

        public boolean apply(CommonGameCharacter character) {
            return character.rank == rank;
        }
    }

    public static class GetByRangePredicate implements Predicate<CommonGameCharacter> {

        Vector3f center;
        float checkRange;

        public GetByRangePredicate(Vector3f center, float range) {
            this.center = center;
            this.checkRange = range;
        }

        public GetByRangePredicate(CommonGameCharacter centerCharacter, float range) {
            this(centerCharacter.getLocation(), range);
        }

        public boolean apply(CommonGameCharacter gameCharacter) {
            boolean inRange = gameCharacter.getLocation().distance(center) < checkRange;
            //System.out.println("InRange " + checkRange + " : " + inRange);
            return inRange;
        }
    }
}
