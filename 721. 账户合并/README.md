# 题目
721. 账户合并

## 解题思路


##
```java
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        if(accounts == null) return null;
        HashMap<Integer,String> nameMap = new HashMap<>();
        HashMap<String,Integer> emailMap = new HashMap<>();
        int size = accounts.size();

        UnionFind uf = new UnionFind(size);
        for(int i=0; i<size;i++){
            List<String> tmp = accounts.get(i);
            //String name = tmp[0];
            String name = tmp.get(0);
            nameMap.put(i,name);

            for(int j=1;j<tmp.size();j++){
                if(emailMap.containsKey(tmp.get(j))){
                    uf.union(emailMap.get(tmp.get(j)),i);
                }else{
                    emailMap.put(tmp.get(j),i);
                }
            }
        }

        Map<Integer,List<String>> mp2 = new HashMap<>();
        for(Map.Entry<String,Integer> e : emailMap.entrySet()){
            String email = e.getKey();
            int index = e.getValue();
            int fatherIndex = uf.findFather(index);
            if(!mp2.containsKey(fatherIndex)){
                mp2.put(fatherIndex,new ArrayList<String>());
                mp2.get(fatherIndex).add(nameMap.get(fatherIndex));
            }else{
                mp2.get(fatherIndex).add(email);
            }
        }

        return new ArrayList<>(mp2.values());
    }
}
class UnionFind {
    HashMap<Integer,Integer> fatherMap;
    HashMap<Integer,Integer> sizeMap;

    public UnionFind(int counts){
        fatherMap = new HashMap<>();
        sizeMap = new HashMap<>();

        for(int i=0;i<counts;i++){
            fatherMap.put(i,i);
            sizeMap.put(i,1);
        }
    }


    public int findFather(int val){
        int father = fatherMap.get(val);
        if(father != val){
            father = findFather(father);
        }
        fatherMap.put(val,father);
        return father;
    }

    public void union(int a,int b){
        int aFather = findFather(a);
        int bFather = findFather(b);
        fatherMap.put(bFather,aFather);
    }
}
```

```java
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        DSU dsu = new DSU();
        Map<String, String> emailToName = new HashMap();
        Map<String, Integer> emailToID = new HashMap();
        int id = 0;
        for (List<String> account: accounts) {
            String name = "";
            for (String email: account) {
                if (name == "") {
                    name = email;
                    continue;
                }
                emailToName.put(email, name);
                if (!emailToID.containsKey(email)) {
                    emailToID.put(email, id++);
                }
                dsu.union(emailToID.get(account.get(1)), emailToID.get(email));
            }
        }

        Map<Integer, List<String>> ans = new HashMap();
        for (String email: emailToName.keySet()) {
            int index = dsu.find(emailToID.get(email));
            ans.computeIfAbsent(index, x-> new ArrayList()).add(email);
        }
        for (List<String> component: ans.values()) {
            Collections.sort(component);
            component.add(0, emailToName.get(component.get(0)));
        }
        return new ArrayList(ans.values());
    }
}
class DSU {
    int[] parent;
    public DSU() {
        parent = new int[10001];
        for (int i = 0; i <= 10000; ++i)
            parent[i] = i;
    }
    public int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }
    public void union(int x, int y) {
        parent[find(x)] = find(y);
    }
}

```


