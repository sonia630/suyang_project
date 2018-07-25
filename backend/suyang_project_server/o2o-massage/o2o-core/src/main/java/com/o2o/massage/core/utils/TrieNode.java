package com.o2o.massage.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典树节点
 */
public class TrieNode {
    //字符
    private Character character;
    //节点对应的子节点
    private Map<Character, TrieNode> nodeMap;
    //标识当前的节点是不是一个完整的词
    private boolean ifWord = false;

    public TrieNode(Character character, Map<Character, TrieNode> nodeSet) {
        this.character = character;
        this.nodeMap = nodeSet;
    }

    /**
     * 添加字符
     *
     * @param c
     * @return
     */
    public TrieNode addCharacter(Character c) {
        if (nodeMap == null) {
            this.nodeMap = new HashMap<Character, TrieNode>();
        }
        TrieNode node = nodeMap.get(c);
        if (node == null) {
            TrieNode node1 = new TrieNode(c, null);
            nodeMap.put(c, node1);
            node = node1;
            //node.getMap().put(c,new TrieNode(c,new HashMap<Character, TrieNode>()));
        }
        return node;
    }

    public Map<Character, TrieNode> getMap() {
        return this.nodeMap;
    }

    public TrieNode getCharacter(Character c) {
        return nodeMap.get(c);
    }

    public boolean isIfWord() {
        return ifWord;
    }

    public void setIfWord(boolean ifWord) {
        this.ifWord = ifWord;
    }

    public Character getCharacter() {
        return character;
    }

    public Map<Character, TrieNode> getNodeMap() {
        return nodeMap;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setNodeMap(Map<Character, TrieNode> nodeMap) {
        this.nodeMap = nodeMap;
    }
}

