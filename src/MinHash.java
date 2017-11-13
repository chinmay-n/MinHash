import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import com.idealista.*;
import com.idealista.tlsh.TLSH;
import com.idealista.tlsh.digests.Digest;
import com.idealista.tlsh.digests.DigestBuilder;

/**
* A simple MinHash implementation inspired by https://github.com/jmhodges/minhash
*
* @author tpeng (pengtaoo@gmail.com)
*/

public class MinHash {

    private HashFunction hash = Hashing.sha512();
    		
    public String hash(String string) {
        int min = Integer.MAX_VALUE;
        for (int i=0; i<string.length(); i++) {
            int c = string.charAt(i);
            int n = hash.hashInt(c).asInt();
            if (n < min) {
                min = n;
            }
        }
        return Integer.toHexString(min);
    }

    public static void main(String[] args) {
        MinHash minHash = new MinHash();
        String content = "abcd";
        List<String> lines=null;
		try {
			lines = Files.readAllLines(Paths.get("/home/chinmay/eclipse-workspace/LSH/part-r-00000"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s1,s2,c1,c2;StringTokenizer itr,itr2;
		Digest d1;
		Digest d2;int diff=999;
		for(int i = 0 ; i< lines.size() ;i++) {
			itr = new StringTokenizer(Iterables.get(lines, i).toString());
			s1 = itr.nextToken().toString();
			s2 = itr.nextToken().toString();
			for(int j=0;j<lines.size();j++) {
				diff=999;
				if(i!=j && i<j) {
					itr2=new StringTokenizer(Iterables.get(lines, j).toString());
					c1 = itr2.nextToken().toString();
					c2 = itr2.nextToken().toString();
					//System.out.println(s2+" "+c2);
					d1=new DigestBuilder().withHash(s2).build();
					d2=new DigestBuilder().withHash(c2).build();
					diff = d2.calculateDifference(d1, true);
					if(diff<100) {
						System.out.println(s1+" and "+c1 + " may be similar");	
					}
				}
			}
		}
	
        //Collection a = Collections2.orderedPermutations(lines);
        //System.out.println("A = "+Iterables.get(a, 1).toString());
        //System.out.println(minHash.hash(Iterables.get(a, 31).toString()));
        //System.out.println(minHash.hash(Iterables.get(a, 20).toString()));
        //System.out.println(minHash.hash(Iterables.get(a, 21).toString()));
        
    }
}