package collision;

import java.util.Arrays;

public class Intersection {

	/* Triangle/triangle intersection test routine,
	 * by Tomas Moller, 1997.
	 * See article "A Fast Triangle-Triangle Intersection Test",
	 * Journal of Graphics Tools, 2(2), 1997
	 *
	 * Updated June 1999: removed the divisions -- a little faster now!
	 * Updated October 1999: added {} to CROSS and SUB macros 
	 *
	 * int NoDivTriTriIsect(float V0[3],float V1[3],float V2[3],
	 *                      float U0[3],float U1[3],float U2[3])
	 *
	 * parameters: vertices of triangle 1: V0,V1,V2
	 *             vertices of triangle 2: U0,U1,U2
	 * result    : returns 1 if the triangles intersect, otherwise 0
	 *
	 */
//
//	#include <math.h>
//	#define FABS(x) (float(fabs(x)))        /* implement as is fastest on your machine */
	
	private static float fabs(float x){
		return x > 0?x:-x;
	}
//
//	/* if USE_EPSILON_TEST is true then we do a check:
//	         if |dv|<EPSILON then dv=0.0;
//	   else no check is done (which is less robust)
//	*/
//	#define USE_EPSILON_TEST TRUE
//	#define EPSILON 0.000001
//
//
//	/* some macros */
//	#define CROSS(dest,v1,v2){                     \
//	              dest[0]=v1[1]*v2[2]-v1[2]*v2[1]; \
//	              dest[1]=v1[2]*v2[0]-v1[0]*v2[2]; \
//	              dest[2]=v1[0]*v2[1]-v1[1]*v2[0];}
//
	private static MyVector3f cross(MyVector3f v1, MyVector3f v2){
		return new MyVector3f(v1.y*v2.z-v1.z*v2.y, 
						v1.z*v2.x-v1.x*v2.z,
						v1.x*v2.y-v1.y*v2.x);
	}
	
//	#define DOT(v1,v2) (v1[0]*v2[0]+v1[1]*v2[1]+v1[2]*v2[2])
//
	private static float dot(MyVector3f v1, MyVector3f v2){
		return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
	}
	
	
//	#define SUB(dest,v1,v2){         \
//	            dest[0]=v1[0]-v2[0]; \
//	            dest[1]=v1[1]-v2[1]; \
//	            dest[2]=v1[2]-v2[2];}
//
	private static MyVector3f sub(MyVector3f v1, MyVector3f v2){
		return new MyVector3f(v1.x-v2.x, 
							v1.y-v2.y,
							v1.z-v2.z);
	}
	
//	/* sort so that a<=b */
//	#define SORT(a,b)       \
//	             if(a>b)    \
//	             {          \
//	               float c; \
//	               c=a;     \
//	               a=b;     \
//	               b=c;     \
//	             }
//
	

	
	
//
//	/* this edge to edge test is based on Franlin Antonio's gem:
//	   "Faster Line Segment Intersection", in Graphics Gems III,
//	   pp. 199-202 */
//	#define EDGE_EDGE_TEST(V0,U0,U1)                      \
//	  Bx=U0[i0]-U1[i0];                                   \
//	  By=U0[i1]-U1[i1];                                   \
//	  Cx=V0[i0]-U0[i0];                                   \
//	  Cy=V0[i1]-U0[i1];                                   \
//	  f=Ay*Bx-Ax*By;                                      \
//	  d=By*Cx-Bx*Cy;                                      \
//	  if((f>0 && d>=0 && d<=f) || (f<0 && d<=0 && d>=f))  \
//	  {                                                   \
//	    e=Ax*Cy-Ay*Cx;                                    \
//	    if(f>0)                                           \
//	    {                                                 \
//	      if(e>=0 && e<=f) return 1;                      \
//	    }                                                 \
//	    else                                              \
//	    {                                                 \
//	      if(e<=0 && e>=f) return 1;                      \
//	    }                                                 \
//	  }
//
	
	private static int EDGE_EDGE_TEST(MyVector3f V0,MyVector3f U0,MyVector3f U1, Data1 data, int i0, int i1){
	  data.Bx=U0.get(i0)-U1.get(i0);                                   
	  data.By=U0.get(i1)-U1.get(i1);                                   
	  data.Cx=V0.get(i0)-U0.get(i0);                                   
	  data.Cy=V0.get(i1)-U0.get(i1);                                   
	  data.f=data.Ay*data.Bx-data.Ax*data.By;                                      
	  data.d=data.By*data.Cx-data.Bx*data.Cy;                                      
	  if((data.f>0 && data.d>=0 && data.d<=data.f) || (data.f<0 && data.d<=0 && data.d>=data.f)) 
	  {                                                   
		  data.e=data.Ax*data.Cy-data.Ay*data.Cx;                                    
	    if(data.f>0)                                           
	    {                                                 
	      if(data.e>=0 && data.e<=data.f) return 1;                      
	    }                                                 
	    else                                              
	    {                                                 
	      if(data.e<=0 && data.e>=data.f) return 1;                      
	    }                                                 
	  }
	  return 0;
	}
	
	
	
//	#define EDGE_AGAINST_TRI_EDGES(V0,V1,U0,U1,U2) \
//	{                                              \
//	  float Ax,Ay,Bx,By,Cx,Cy,e,d,f;               \
//	  Ax=V1[i0]-V0[i0];                            \
//	  Ay=V1[i1]-V0[i1];                            \
//	  /* test edge U0,U1 against V0,V1 */          \
//	  EDGE_EDGE_TEST(V0,U0,U1);                    \
//	  /* test edge U1,U2 against V0,V1 */          \
//	  EDGE_EDGE_TEST(V0,U1,U2);                    \
//	  /* test edge U2,U1 against V0,V1 */          \
//	  EDGE_EDGE_TEST(V0,U2,U0);                    \
//	}
	
	private static class Data1 {
		public float Ax,Ay,Bx,By,Cx,Cy,e,d,f; 
	}

	private static int EDGE_AGAINST_TRI_EDGES(MyVector3f V0,MyVector3f V1, MyVector3f U0, MyVector3f U1, MyVector3f U2, int i0, int i1) 
	{                                              
	  Data1 data = new Data1();      
	  data.Ax=V1.get(i0)-V0.get(i0);                            
	  data.Ay=V1.get(i1)-V0.get(i1);                            
	  /* test edge U0,U1 against V0,V1 */          
	  if(0 != EDGE_EDGE_TEST(V0,U0,U1,data, i0, i1) ||                  
	  /* test edge U1,U2 against V0,V1 */          
	  0 != EDGE_EDGE_TEST(V0,U1,U2,data, i0, i1) ||                
	  /* test edge U2,U1 against V0,V1 */          
	  0 != EDGE_EDGE_TEST(V0,U2,U0,data, i0, i1)){
		  return 1;
	  }
	  
	  return 0;
	}
	
	
	
//	#define POINT_IN_TRI(V0,U0,U1,U2)           \
//	{                                           \
//	  float a,b,c,d0,d1,d2;                     \
//	  /* is T1 completly inside T2? */          \
//	  /* check if V0 is inside tri(U0,U1,U2) */ \
//	  a=U1[i1]-U0[i1];                          \
//	  b=-(U1[i0]-U0[i0]);                       \
//	  c=-a*U0[i0]-b*U0[i1];                     \
//	  d0=a*V0[i0]+b*V0[i1]+c;                   \
//	                                            \
//	  a=U2[i1]-U1[i1];                          \
//	  b=-(U2[i0]-U1[i0]);                       \
//	  c=-a*U1[i0]-b*U1[i1];                     \
//	  d1=a*V0[i0]+b*V0[i1]+c;                   \
//	                                            \
//	  a=U0[i1]-U2[i1];                          \
//	  b=-(U0[i0]-U2[i0]);                       \
//	  c=-a*U2[i0]-b*U2[i1];                     \
//	  d2=a*V0[i0]+b*V0[i1]+c;                   \
//	  if(d0*d1>0.0)                             \
//	  {                                         \
//	    if(d0*d2>0.0) return 1;                 \
//	  }                                         \
//	}
//
	
	
	private static int POINT_IN_TRI(MyVector3f V0,MyVector3f U0,MyVector3f U1,MyVector3f U2, int i0, int i1)
	{
	  float a,b,c,d0,d1,d2;
	  /* is T1 completly inside T2? */
	  /* check if V0 is inside tri(U0,U1,U2) */
	  a=U1.get(i1)-U0.get(i1);
	  b=-(U1.get(i0)-U0.get(i0));
	  c=-a*U0.get(i0)-b*U0.get(i1);
	  d0=a*V0.get(i0)+b*V0.get(i1)+c;

	  a=U2.get(i1)-U1.get(i1);
	  b=-(U2.get(i0)-U1.get(i0));
	  c=-a*U1.get(i0)-b*U1.get(i1);
	  d1=a*V0.get(i0)+b*V0.get(i1)+c;

	  a=U0.get(i1)-U2.get(i1);
	  b=-(U0.get(i0)-U2.get(i0));
	  c=-a*U2.get(i0)-b*U2.get(i1);
	  d2=a*V0.get(i0)+b*V0.get(i1)+c;
	  if(d0*d1>0.0){
	    if(d0*d2>0.0) return 1;
	  }
	  return 0;
	}
	
	
//	int coplanar_tri_tri(float N[3],float V0[3],float V1[3],float V2[3],
//	                     float U0[3],float U1[3],float U2[3])
//	{
//	   float A[3];
//	   short i0,i1;
//	   /* first project onto an axis-aligned plane, that maximizes the area */
//	   /* of the triangles, compute indices: i0,i1. */
//	   A[0]=FABS(N[0]);
//	   A[1]=FABS(N[1]);
//	   A[2]=FABS(N[2]);
//	   if(A[0]>A[1])
//	   {
//	      if(A[0]>A[2])
//	      {
//	          i0=1;      /* A[0] is greatest */
//	          i1=2;
//	      }
//	      else
//	      {
//	          i0=0;      /* A[2] is greatest */
//	          i1=1;
//	      }
//	   }
//	   else   /* A[0]<=A[1] */
//	   {
//	      if(A[2]>A[1])
//	      {
//	          i0=0;      /* A[2] is greatest */
//	          i1=1;
//	      }
//	      else
//	      {
//	          i0=0;      /* A[1] is greatest */
//	          i1=2;
//	      }
//	    }
//
//	    /* test all edges of triangle 1 against the edges of triangle 2 */
//	    EDGE_AGAINST_TRI_EDGES(V0,V1,U0,U1,U2);
//	    EDGE_AGAINST_TRI_EDGES(V1,V2,U0,U1,U2);
//	    EDGE_AGAINST_TRI_EDGES(V2,V0,U0,U1,U2);
//
//	    /* finally, test if tri1 is totally contained in tri2 or vice versa */
//	    POINT_IN_TRI(V0,U0,U1,U2);
//	    POINT_IN_TRI(U0,V0,V1,V2);
//
//	    return 0;
//	}
//

	private static int coplanar_tri_tri(MyVector3f N, MyVector3f V0, MyVector3f V1,MyVector3f V2, MyVector3f U0, MyVector3f U1, MyVector3f U2) {
		float A[] = new float[3];
		short i0, i1;
		/* first project onto an axis-aligned plane, that maximizes the area */
		/* of the triangles, compute indices: i0,i1. */
		A[0] = fabs(N.get(0));
		A[1] = fabs(N.get(1));
		A[2] = fabs(N.get(2));
		if (A[0] > A[1]) {
			if (A[0] > A[2]) {
				i0 = 1; /* A[0] is greatest */
				i1 = 2;
			} else {
				i0 = 0; /* A[2] is greatest */
				i1 = 1;
			}
		} else /* A[0]<=A[1] */
		{
			if (A[2] > A[1]) {
				i0 = 0; /* A[2] is greatest */
				i1 = 1;
			} else {
				i0 = 0; /* A[1] is greatest */
				i1 = 2;
			}
		}

		/* test all edges of triangle 1 against the edges of triangle 2 */
		if ( 0 != EDGE_AGAINST_TRI_EDGES(V0, V1, U0, U1, U2, i0, i1) ||
		0 != EDGE_AGAINST_TRI_EDGES(V1, V2, U0, U1, U2, i0, i1) ||
		0 != EDGE_AGAINST_TRI_EDGES(V2, V0, U0, U1, U2, i0, i1) ||
		/* finally, test if tri1 is totally contained in tri2 or vice versa */
		0 != POINT_IN_TRI(V0, U0, U1, U2, i0, i1) ||
		0 != POINT_IN_TRI(U0, V0, V1, V2, i0, i1)){
			return 1;
		}

		return 0;
	}
	
	
	
//
//	#define NEWCOMPUTE_INTERVALS(VV0,VV1,VV2,D0,D1,D2,D0D1,D0D2,A,B,C,X0,X1) \
//	{ \
//	        if(D0D1>0.0f) \
//	        { \
//	                /* here we know that D0D2<=0.0 */ \
//	            /* that is D0, D1 are on the same side, D2 on the other or on the plane */ \
//	                A=VV2; B=(VV0-VV2)*D2; C=(VV1-VV2)*D2; X0=D2-D0; X1=D2-D1; \
//	        } \
//	        else if(D0D2>0.0f)\
//	        { \
//	                /* here we know that d0d1<=0.0 */ \
//	            A=VV1; B=(VV0-VV1)*D1; C=(VV2-VV1)*D1; X0=D1-D0; X1=D1-D2; \
//	        } \
//	        else if(D1*D2>0.0f || D0!=0.0f) \
//	        { \
//	                /* here we know that d0d1<=0.0 or that D0!=0.0 */ \
//	                A=VV0; B=(VV1-VV0)*D0; C=(VV2-VV0)*D0; X0=D0-D1; X1=D0-D2; \
//	        } \
//	        else if(D1!=0.0f) \
//	        { \
//	                A=VV1; B=(VV0-VV1)*D1; C=(VV2-VV1)*D1; X0=D1-D0; X1=D1-D2; \
//	        } \
//	        else if(D2!=0.0f) \
//	        { \
//	                A=VV2; B=(VV0-VV2)*D2; C=(VV1-VV2)*D2; X0=D2-D0; X1=D2-D1; \
//	        } \
//	        else \
//	        { \
//	                /* triangles are coplanar */ \
//	                return coplanar_tri_tri(N1,V0,V1,V2,U0,U1,U2); \
//	        } \
	
	private static float[] NEWCOMPUTE_INTERVALS(float VV0,float VV1,float VV2,float D0,float D1,float D2,float D0D1,float D0D2,
			MyVector3f N1,MyVector3f V0,MyVector3f V1,MyVector3f V2,MyVector3f U0,MyVector3f U1,MyVector3f U2)
	{
		float[] result = new float[5];
	        if(D0D1>0.0f){
	                /* here we know that D0D2<=0.0 */ 
	            /* that is D0, D1 are on the same side, D2 on the other or on the plane */ 
	        	result[0]=VV2; 
	        	result[1]=(VV0-VV2)*D2; 
	        	result[2]=(VV1-VV2)*D2; 
	        	result[3]=D2-D0; 
	        	result[4]=D2-D1; 
	        } 
	        else if(D0D2>0.0f)
	        { 
	                /* here we know that d0d1<=0.0 */ 
	        	result[0]=VV1; 
	        	result[1]=(VV0-VV1)*D1; 
	        	result[2]=(VV2-VV1)*D1; 
	        	result[3]=D1-D0; 
	        	result[4]=D1-D2; 
	        } 
	        else if(D1*D2>0.0f || D0!=0.0f) 
	        { 
	                /* here we know that d0d1<=0.0 or that D0!=0.0 */ 
	        	result[0]=VV0; 
	        	result[1]=(VV1-VV0)*D0; 
	        	result[2]=(VV2-VV0)*D0; 
	        	result[3]=D0-D1; 
	        	result[4]=D0-D2; 
	        } 
	        else if(D1!=0.0f) 
	        { 
	        	result[0]=VV1; 
	        	result[1]=(VV0-VV1)*D1; 
	        	result[2]=(VV2-VV1)*D1; 
	        	result[3]=D1-D0; 
	        	result[4]=D1-D2; 
	        } 
	        else if(D2!=0.0f) 
	        { 
	        	result[0]=VV2; 
	        	result[1]=(VV0-VV2)*D2; 
	        	result[2]=(VV1-VV2)*D2; 
	        	result[3]=D2-D0; 
	        	result[4]=D2-D1; 
	        } 
	        else 
	        { 
	                /* triangles are coplanar */ 
	                if(0 == coplanar_tri_tri(N1,V0,V1,V2,U0,U1,U2)) {
	                	result = new float[0];
	                }else{
	                	result = new float[1];
	                }
	        } 
	        
	        return result;
	}


	
	
	
	
//
//	int NoDivTriTriIsect(float V0[3],float V1[3],float V2[3],
//	                     float U0[3],float U1[3],float U2[3])
//	{
//	  float E1[3],E2[3];
//	  float N1[3],N2[3],d1,d2;
//	  float du0,du1,du2,dv0,dv1,dv2;
//	  float D[3];
//	  float isect1[2], isect2[2];
//	  float du0du1,du0du2,dv0dv1,dv0dv2;
//	  short index;
//	  float vp0,vp1,vp2;
//	  float up0,up1,up2;
//	  float bb,cc,max;
//
//	  /* compute plane equation of triangle(V0,V1,V2) */
//	  SUB(E1,V1,V0);
//	  SUB(E2,V2,V0);
//	  CROSS(N1,E1,E2);
//	  d1=-DOT(N1,V0);
//	  /* plane equation 1: N1.X+d1=0 */
//
//	  /* put U0,U1,U2 into plane equation 1 to compute signed distances to the plane*/
//	  du0=DOT(N1,U0)+d1;
//	  du1=DOT(N1,U1)+d1;
//	  du2=DOT(N1,U2)+d1;
//
//	  /* coplanarity robustness check */
//	#if USE_EPSILON_TEST==TRUE
//	  if(FABS(du0)<EPSILON) du0=0.0;
//	  if(FABS(du1)<EPSILON) du1=0.0;
//	  if(FABS(du2)<EPSILON) du2=0.0;
//	#endif
//	  du0du1=du0*du1;
//	  du0du2=du0*du2;
//
//	  if(du0du1>0.0f && du0du2>0.0f) /* same sign on all of them + not equal 0 ? */
//	    return 0;                    /* no intersection occurs */
//
//	  /* compute plane of triangle (U0,U1,U2) */
//	  SUB(E1,U1,U0);
//	  SUB(E2,U2,U0);
//	  CROSS(N2,E1,E2);
//	  d2=-DOT(N2,U0);
//	  /* plane equation 2: N2.X+d2=0 */
//
//	  /* put V0,V1,V2 into plane equation 2 */
//	  dv0=DOT(N2,V0)+d2;
//	  dv1=DOT(N2,V1)+d2;
//	  dv2=DOT(N2,V2)+d2;
//
//	#if USE_EPSILON_TEST==TRUE
//	  if(FABS(dv0)<EPSILON) dv0=0.0;
//	  if(FABS(dv1)<EPSILON) dv1=0.0;
//	  if(FABS(dv2)<EPSILON) dv2=0.0;
//	#endif
//
//	  dv0dv1=dv0*dv1;
//	  dv0dv2=dv0*dv2;
//
//	  if(dv0dv1>0.0f && dv0dv2>0.0f) /* same sign on all of them + not equal 0 ? */
//	    return 0;                    /* no intersection occurs */
//
//	  /* compute direction of intersection line */
//	  CROSS(D,N1,N2);
//
//	  /* compute and index to the largest component of D */
//	  max=(float)FABS(D[0]);
//	  index=0;
//	  bb=(float)FABS(D[1]);
//	  cc=(float)FABS(D[2]);
//	  if(bb>max) max=bb,index=1;
//	  if(cc>max) max=cc,index=2;
//
//	  /* this is the simplified projection onto L*/
//	  vp0=V0[index];
//	  vp1=V1[index];
//	  vp2=V2[index];
//
//	  up0=U0[index];
//	  up1=U1[index];
//	  up2=U2[index];
//
//	  /* compute interval for triangle 1 */
//	  float a,b,c,x0,x1;
//	  NEWCOMPUTE_INTERVALS(vp0,vp1,vp2,dv0,dv1,dv2,dv0dv1,dv0dv2,a,b,c,x0,x1);
//
//	  /* compute interval for triangle 2 */
//	  float d,e,f,y0,y1;
//	  NEWCOMPUTE_INTERVALS(up0,up1,up2,du0,du1,du2,du0du1,du0du2,d,e,f,y0,y1);
//
//	  float xx,yy,xxyy,tmp;
//	  xx=x0*x1;
//	  yy=y0*y1;
//	  xxyy=xx*yy;
//
//	  tmp=a*xxyy;
//	  isect1[0]=tmp+b*x1*yy;
//	  isect1[1]=tmp+c*x0*yy;
//
//	  tmp=d*xxyy;
//	  isect2[0]=tmp+e*xx*y1;
//	  isect2[1]=tmp+f*xx*y0;
//
//	  SORT(isect1[0],isect1[1]);
//	  SORT(isect2[0],isect2[1]);
//
//	  if(isect1[1]<isect2[0] || isect2[1]<isect1[0]) return 0;
//	  return 1;
//	}
//
	
//	public static int NoDivTriTriIsect(Face f1, Face f2){
	public static int NoDivTriTriIsect(MyVector3f V0,MyVector3f V1,MyVector3f V2,MyVector3f U0,MyVector3f U1,MyVector3f U2){
		MyVector3f E1;
		MyVector3f E2;
		  
		MyVector3f N1;
		MyVector3f N2;
		  float d1,d2;
		  
		  float du0,du1,du2,dv0,dv1,dv2;
		  MyVector3f D;
		  float isect1[] = new float[2];
		  float isect2[] = new float[2];;
		  float du0du1,du0du2,dv0dv1,dv0dv2;
		  short index;
		  float vp0,vp1,vp2;
		  float up0,up1,up2;
		  float bb,cc,max;
		  
		  
//		  /* compute plane equation of triangle(V0,V1,V2) */
//		  SUB(E1,V1,V0);
//		  SUB(E2,V2,V0);
//		  CROSS(N1,E1,E2);
//		  d1=-DOT(N1,V0);
//		  /* plane equation 1: N1.X+d1=0 */
		  
		  E1 = sub(V1, V0);
		  E2 = sub(V2, V0);
		  N1 = cross(E1, E2);
		  d1=-dot(N1,V0);
		
//		  /* put U0,U1,U2 into plane equation 1 to compute signed distances to the plane*/
//		  du0=DOT(N1,U0)+d1;
//		  du1=DOT(N1,U1)+d1;
//		  du2=DOT(N1,U2)+d1;
	
		  du0=dot(N1,U0)+d1;
		  du1=dot(N1,U1)+d1;
		  du2=dot(N1,U2)+d1;
		  
		  
		  
//		  /* coplanarity robustness check */
//		#if USE_EPSILON_TEST==TRUE
//		  if(FABS(du0)<EPSILON) du0=0.0;
//		  if(FABS(du1)<EPSILON) du1=0.0;
//		  if(FABS(du2)<EPSILON) du2=0.0;
//		#endif
//		  du0du1=du0*du1;
//		  du0du2=du0*du2;
	
		  du0du1=du0*du1;
		  du0du2=du0*du2;
		  
//		  if(du0du1>0.0f && du0du2>0.0f) /* same sign on all of them + not equal 0 ? */
//		    return 0;                    /* no intersection occurs */
	
		  if(du0du1>0.0f && du0du2>0.0f)
			  return 0;
		  
//		  /* compute plane of triangle (U0,U1,U2) */
//		  SUB(E1,U1,U0);
//		  SUB(E2,U2,U0);
//		  CROSS(N2,E1,E2);
//		  d2=-DOT(N2,U0);
//		  /* plane equation 2: N2.X+d2=0 */
	
		 E1 = sub(U1,U0);
		 E2 = sub(U2,U0);
		 N2 = cross(E1,E2);
		 d2=-dot(N2,U0);
		  
//		  /* put V0,V1,V2 into plane equation 2 */
//		  dv0=DOT(N2,V0)+d2;
//		  dv1=DOT(N2,V1)+d2;
//		  dv2=DOT(N2,V2)+d2;
	
		  dv0=dot(N2,V0)+d2;
		  dv1=dot(N2,V1)+d2;
		  dv2=dot(N2,V2)+d2;	 
		 	 
		 
//		#if USE_EPSILON_TEST==TRUE
//		  if(FABS(dv0)<EPSILON) dv0=0.0;
//		  if(FABS(dv1)<EPSILON) dv1=0.0;
//		  if(FABS(dv2)<EPSILON) dv2=0.0;
//		#endif
	//
//		  dv0dv1=dv0*dv1;
//		  dv0dv2=dv0*dv2;
	
		  dv0dv1=dv0*dv1;
		  dv0dv2=dv0*dv2;  
		  
//		  if(dv0dv1>0.0f && dv0dv2>0.0f) /* same sign on all of them + not equal 0 ? */
//		    return 0;                    /* no intersection occurs */
	
		  if(dv0dv1>0.0f && dv0dv2>0.0f)
			  return 0;
		  
//		  /* compute direction of intersection line */
//		  CROSS(D,N1,N2);
	
		  D = cross(N1, N2);
		  
//		  /* compute and index to the largest component of D */
//		  max=(float)FABS(D[0]);
//		  index=0;
//		  bb=(float)FABS(D[1]);
//		  cc=(float)FABS(D[2]);
//		  if(bb>max) max=bb,index=1;
//		  if(cc>max) max=cc,index=2;
	
		  max=fabs(D.x);
		  index=0;
		  bb=fabs(D.y);
		  cc=fabs(D.z);
		  
//		  /* this is the simplified projection onto L*/
//		  vp0=V0[index];
//		  vp1=V1[index];
//		  vp2=V2[index];
		  
//		  up0=U0[index];
//		  up1=U1[index];
//		  up2=U2[index];
		  
		  vp0=V0.x;
		  vp1=V1.x;
		  vp2=V2.x;
		  up0=U0.x;
		  up1=U1.x;
		  up2=U2.x;
		  
		  if(bb>max){
			  max=bb;
			  index=1;
			  vp0=V0.y;
			  vp1=V1.y;
			  vp2=V2.y;
			  
			  up0=U0.y;
			  up1=U1.y;
			  up2=U2.y;
		  }
		  if(cc>max){
			  max=cc;
			  index=2;
			  vp0=V0.z;
			  vp1=V1.z;
			  vp2=V2.z;
			  
			  up0=U0.z;
			  up1=U1.z;
			  up2=U2.z;
		  }
		  
		  
		  

	//
//		  /* compute interval for triangle 1 */
//		  float a,b,c,x0,x1;
//		  NEWCOMPUTE_INTERVALS(vp0,vp1,vp2,dv0,dv1,dv2,dv0dv1,dv0dv2,a,b,c,x0,x1);
		  
		  
		  float[] result = NEWCOMPUTE_INTERVALS(vp0,vp1,vp2,dv0,dv1,dv2,dv0dv1,dv0dv2,N1,V0,V1,V2,U0,U1,U2);
		  if(result.length == 1){
			  return 1;
		  }else if(result.length == 0){
			  return 0;
		  }
		  float a = result[0];
		  float b = result[1];
		  float c = result[2];
		  float x0 = result[3];
		  float x1 = result[4];
		  
//		  /* compute interval for triangle 2 */
//		  float d,e,f,y0,y1;
//		  NEWCOMPUTE_INTERVALS(up0,up1,up2,du0,du1,du2,du0du1,du0du2,d,e,f,y0,y1);

		  result = NEWCOMPUTE_INTERVALS(up0,up1,up2,du0,du1,du2,du0du1,du0du2,N1,V0,V1,V2,U0,U1,U2);
		  if(result.length == 1){
			  return 1;
		  }else if(result.length == 0){
			  return 0;
		  }
		  float d = result[0];
		  float e = result[1];
		  float f = result[2];
		  float y0 = result[3];
		  float y1 = result[4];
		  
		  
//		  float xx,yy,xxyy,tmp;
//		  xx=x0*x1;
//		  yy=y0*y1;
//		  xxyy=xx*yy;
	
		  float xx,yy,xxyy,tmp;
		  xx=x0*x1;
		  yy=y0*y1;
		  xxyy=xx*yy;
		   
//		  tmp=a*xxyy;
//		  isect1[0]=tmp+b*x1*yy;
//		  isect1[1]=tmp+c*x0*yy;
		  
		  tmp=a*xxyy;
		  isect1[0]=tmp+b*x1*yy;
		  isect1[1]=tmp+c*x0*yy;

//		  tmp=d*xxyy;
//		  isect2[0]=tmp+e*xx*y1;
//		  isect2[1]=tmp+f*xx*y0;
		  
		  tmp=d*xxyy;
		  isect2[0]=tmp+e*xx*y1;
		  isect2[1]=tmp+f*xx*y0;
		  
//		  SORT(isect1[0],isect1[1]);
//		  SORT(isect2[0],isect2[1]);
		  
		  Arrays.sort(isect1);
		  Arrays.sort(isect2);
		  
//		  if(isect1[1]<isect2[0] || isect2[1]<isect1[0]) return 0;
//		  return 1;
		
		  if(isect1[1]<isect2[0] || isect2[1]<isect1[0]) return 0;
		  return 1;
	}

}
