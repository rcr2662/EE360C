import java.util.Arrays;

public class OldProgram3 {

    public int maxFoodCount (int[] sections) {
        if (sections.length < 2) return 0;

        int wall = (sections.length - 1)/2;
        int bwall = wall;
        int leftSum = 0;
        int rightSum = 0;
        int smaller;
        int max = 0;

        for (int i = 0; i <= wall; i++){
            leftSum += sections[i];
        }
        System.out.println(leftSum);
        for(int i = wall + 1; i < sections.length; i++){
            rightSum += sections[i];
        }
        System.out.println(rightSum);

        if(leftSum < rightSum){
            smaller = leftSum;
            do{
                if(max != smaller) bwall = wall;
                max = smaller;
                wall++;
                System.out.println("wall " + sections[wall]);
                leftSum += sections[wall];
                System.out.println(leftSum);
                rightSum -= sections[wall];
                System.out.println(rightSum);
                smaller = leftSum < rightSum ? leftSum : rightSum;
            }while(!(smaller < max));
            leftSum -= sections[wall];
            rightSum += sections[wall];
            wall--;
        }else if (leftSum > rightSum){
            smaller = rightSum;
            do{
                if(max != smaller) bwall = wall;
                max = smaller;
                System.out.println("wall " + sections[wall]);
                leftSum -= sections[wall];
                System.out.println(leftSum);
                rightSum += sections[wall];
                System.out.println(rightSum);
                wall--;
                smaller = leftSum < rightSum ? leftSum : rightSum;
            }while(!(smaller < max));
            wall++;
            leftSum += sections[wall];
            rightSum -= sections[wall];
        }else{
            return leftSum + Math.max(maxFoodCount(Arrays.copyOfRange(sections, 0, wall + 1)), 
                            maxFoodCount(Arrays.copyOfRange(sections, wall + 1, sections.length)));
        }
        System.out.println("max " + max);
        System.out.println("left " + leftSum);
        System.out.println("right " + rightSum);
        System.out.println("wall before rec " + wall);
        if(bwall == wall){
            if(max == leftSum){
                return max + maxFoodCount(Arrays.copyOfRange(sections, 0, wall + 1));
            }else return max + maxFoodCount(Arrays.copyOfRange(sections, wall + 1, sections.length));
        }else{
            if (max == leftSum){
                return max + Math.max(maxFoodCount(Arrays.copyOfRange(sections, 0, wall + 1)), maxFoodCount(Arrays.copyOfRange(sections, bwall + 1, sections.length)));
            }else{
                return max + Math.max(maxFoodCount(Arrays.copyOfRange(sections, 0, bwall + 1)), maxFoodCount(Arrays.copyOfRange(sections, wall + 1, sections.length)));
            }
        }
    }
}
