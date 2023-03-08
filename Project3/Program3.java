public class Program3 {
    public int maxFoodCount (int[] sections) {
        int[][]opt = new int[sections.length - 1][sections.length];
        opt[0][sections.length-1] = -1;
        opt[0][0] = 0;
        int leftBound = 0;
        int wall = leftBound;
        int rightBound = 1;
        int leftSum = sections[0];
        int rightSum;
        int starterRightSum = sections[1];
        int prevRightSum;
        int sectionSize = 2;
        int remainingSize;
        int newMax;
        int max = 0;;
        int leftMax;
        int rightMax;
        int flag;
        for(int i = 0; i < sections.length - 1; i++){
            for(int j = 0; j < sections.length; j++){
                opt[i][j] = 0;
            }
        }
        opt[0][sections.length-1] = -1;

        while (rightBound < sections.length){
            opt[leftBound][rightBound] = sections[leftBound] < sections[rightBound] ? sections[leftBound] : sections[rightBound];
            leftBound++;
            rightBound++;
        }
        while((opt[0][sections.length-1] == -1) && sectionSize < sections.length){
            leftBound = 0;
            flag = 1;
            max = 0;
            rightBound = leftBound + sectionSize;
            remainingSize = sections.length;
            remainingSize -= sectionSize + 1;
            rightSum = starterRightSum;
            prevRightSum = rightSum;
            while(remainingSize >= 0){
                wall = leftBound;
                leftSum = sections[leftBound];
                rightSum = prevRightSum + sections[rightBound];
                prevRightSum = rightSum;
                if(flag == 1){
                    starterRightSum = rightSum;
                    flag = 0;
                }
                while(wall < rightBound){
                    leftMax = leftSum + opt[leftBound][wall];
                    if(wall >= rightBound-1) rightMax = sections[rightBound];
                    else rightMax = rightSum + opt[wall + 1][rightBound];
                    newMax = leftMax < rightMax ? leftMax : rightMax;
                    max = newMax > max ? newMax : max;
                    wall++;
                    leftSum += sections[wall];
                    rightSum -= sections[wall];
                }
                opt[leftBound][rightBound] = max;
                max = 0;
                remainingSize--;
                leftBound++;
                rightBound++;
                prevRightSum -= sections[leftBound];
            }
            sectionSize++;

        }
      
        return opt[0][sections.length-1];
    }
}
