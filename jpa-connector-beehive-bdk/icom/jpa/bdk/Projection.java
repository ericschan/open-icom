package icom.jpa.bdk;

public enum Projection implements icom.jpa.dao.Projection {  
  
  EMPTY,
  
  SECURITY,
  
  BASIC,
  
  META,
  
  FULL;
  
  boolean contains(Projection proj) {
	  if (this == Projection.FULL) {
		  if (proj == Projection.SECURITY) {
			  return false;
		  } else {
			  return true;
		  }
	  } else if (this == Projection.META) {
		  if (proj == Projection.SECURITY) {
			  return false;
		  } else if (proj == Projection.FULL) {
			  return false;
		  } else {
			  return true;
		  }
	  } else if (this == Projection.BASIC) {
		  if (proj == Projection.SECURITY) {
			  return false;
		  } else if (proj == Projection.META) {
			  return false;
		  } else if (proj == Projection.FULL) {
			  return false;
		  } else {
			  return true;
		  }
	  } else if (this == Projection.SECURITY) {
		  if (proj == Projection.BASIC) {
			  return false;
		  } else if (proj == Projection.META) {
			  return false;
		  } else if (proj == Projection.FULL) {
			  return false;
		  } else {
			  return true;
		  }
	  } else if (this == Projection.EMPTY) {
		  if (proj != Projection.EMPTY) {
			  return false;
		  } else {
			  return true;
		  }
	  }
	  return false;
  }

  
}
