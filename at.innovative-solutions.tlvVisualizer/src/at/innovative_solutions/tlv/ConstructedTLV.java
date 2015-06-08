package at.innovative_solutions.tlv;

import java.util.List;

public class ConstructedTLV extends TLV {
	List<TLV> _tlvs;
	
	public ConstructedTLV(final ID id, final List<TLV> tlvs) {
		this(id, tlvs, false);
	}
	
	public ConstructedTLV(final ID id, final List<TLV> tlvs, boolean lengthIndefinite) {
		super(id, lengthIndefinite);
		_tlvs = tlvs;
		// TODO test parenting
		for(final TLV t : tlvs)
			t.setParent(this);
	}
	
	public List<TLV> getTLVs() {
		return _tlvs;
	}
	
	public boolean equals(final Object other) {
		if(!(other instanceof ConstructedTLV))
			return false;
		
		if(!super.equals(other))
			return false;
		
		if(_tlvs.size() != ((ConstructedTLV) other)._tlvs.size())
			return false;
		
		boolean subMatch = true;
		for(int n = 0; n < _tlvs.size(); n++)
			subMatch = subMatch & _tlvs.get(n).equals(((ConstructedTLV) other)._tlvs.get(n));
		return subMatch;
	}

	@Override
	public int getLength() {
		int len = 0;
		for(final TLV t : _tlvs)
			len += t.getLength();
		return len;
	}
	
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("ConstructedTLV(");
		sb.append(_id.toString()).append(", ");
		sb.append("<");
		for(final TLV t : _tlvs)
			sb.append(t.toString()).append(", ");
		sb.append(">, ");
		sb.append(_lengthIndefinite).append(")");
		return sb.toString();
	}
	
	public <T> T accept(final Formatter<T> formatter) {
		return formatter.format(this);
	}
}