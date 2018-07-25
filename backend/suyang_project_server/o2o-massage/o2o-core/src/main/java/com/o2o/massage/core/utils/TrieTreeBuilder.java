package com.o2o.massage.core.utils;

import java.util.*;

/**
 * 简易字典树实现
 */
public class TrieTreeBuilder {

    private TrieNode rootNode;

    public TrieTreeBuilder(){
        this.rootNode = new TrieNode(null,null);
    }

    public static TrieTreeBuilder newBuilder(){
        return new TrieTreeBuilder();
    }

    public static void main(String args[]){
        TrieTreeBuilder builder =  TrieTreeBuilder.newBuilder()
                .addString("美利坚")
                .addString("中国人民站起来了")
                .addString("中国共产党");
        System.out.println(builder.getKeyWordsByPrefix("中国"));
    }

    /**
     * 将关键字添加到Trie树中
     * @param keyWord
     * @return
     */
    public TrieTreeBuilder addString(String keyWord){
        TrieNode node = rootNode;
        for(int i=0;i< keyWord.length();i++){
            Character c = keyWord.charAt(i);
            node = node.addCharacter(c);
        }
        node.setIfWord(true);
        return this;
    }

    /**
     * 根据关键字获取Trie树的全部节点
     * @param findStr
     * @return
     */
    public List<String> getKeyWordsByPrefix(String findStr){
        List<String> suggestList = new ArrayList<String>();
        TrieNode findNode = rootNode;
        for(int i=0;i< findStr.length();i++){
            Character c = findStr.charAt(i);
            findNode = findNode.getCharacter(c);
            if (findNode==null){
                break;
            }
        }
        if (findNode.isIfWord()){
            suggestList.add(findStr);
        }
        getAllSubNodes(findNode,findStr,suggestList);
        return suggestList;
    }

    /**
     * 递归遍历全部节点
     * @param node
     * @param prefix
     * @param suggestList
     */
    private void getAllSubNodes(TrieNode node,String prefix,List<String> suggestList){
        Map<Character,TrieNode> map = node.getMap();
        if (map!=null) {
            Set<Character> keySet = map.keySet();
            List<TrieNode> nodeList = new ArrayList<TrieNode>();
            for (Character c : keySet) {
                nodeList.add(map.get(c));
            }
            for (TrieNode subNode : nodeList) {
                Character m = subNode.getCharacter();
                String word = prefix+m;
                if(subNode.isIfWord()){
                    suggestList.add(word);
                }
                getAllSubNodes(subNode,word,suggestList);
            }

        }

    }
}