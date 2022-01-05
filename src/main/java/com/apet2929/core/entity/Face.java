package com.apet2929.core.entity;


import org.lwjgl.system.CallbackI;

public class Face {
    protected static class IndexGroup {
        public static final int NO_VALUE = -1;

        public int idxPos;

        public int idxTextCoord;

        public int idxVecNormal;

        public IndexGroup() {
            idxPos = NO_VALUE;
            idxTextCoord = NO_VALUE;
            idxVecNormal = NO_VALUE;
        }
    }

    private IndexGroup[] idxGroups;

    public Face(String v1, String v2, String v3) {
        idxGroups = new IndexGroup[3];

        idxGroups[0] = parseLine(v1);
        idxGroups[1] = parseLine(v2);
        idxGroups[2] = parseLine(v3);

    }

    private IndexGroup parseLine(String line) {
        IndexGroup group = new IndexGroup();

        String[] lineTokens = line.split("/");
        int length = lineTokens.length;
        group.idxPos = Integer.parseInt(lineTokens[0]) - 1;
        if(length > 1) {
            // Can be empty if the obj does not define texture coordinates
            String textCoord = lineTokens[1];
            group.idxTextCoord = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IndexGroup.NO_VALUE;

            if(length > 2) {
                group.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
            }
        }
        return group;


    }

    public IndexGroup[] getFaceVertexIndices() {
        return idxGroups;
    }


}
