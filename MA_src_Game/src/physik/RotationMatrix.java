package physik;

import org.lwjgl.util.vector.Vector3f;

public class RotationMatrix {
	
    

    public static Vector3f rotate(Vector3f point, Vector3f centerPoint, float alpha, float beta, float gamma)
    {   
        /* 3D Rotation matrix 
        * 
        *  R(alpha,beta,gamma) = Rz (alpha) Ry (beta) Rx (gamma)
        * 
        *  | [cos_a * cos_b]   [cos_a * sin_b * sin_g - sin_a * cos_g]     [cos_a * sin_b * cos_g + sin_a * sin_g] |
        *  | [sin_a * cos_b]   [sin_a * sin_b * sin_g + cos_a * cos_g]     [sin_a * sin_b * cos_g - cos_a * sin_g] |    
        *  | [-sin_b       ]   [cos_b * sin_g                        ]     [cos_b * cos_g                        ] |
        * 
        */
    	float new_rotated_x, new_rotated_y, new_rotated_z;
       
             double sin_a = Math.sin(alpha);
             double sin_b = Math.sin(beta);
             double sin_g = Math.sin(gamma);  

             double cos_a = Math.cos(alpha);
             double cos_b = Math.cos(beta);
             double cos_g = Math.cos(gamma);
             double[] PositionMatrix = {   point.x,   point.y,   point.z};      

             //ReturnMatrix[row][column]
             double[][] RotationMatrix =   {    {cos_a * cos_b,   (cos_a * sin_b * sin_g) - (sin_a * cos_g),   (cos_a * sin_b * cos_g) + (sin_a * sin_g)  },
                                                {sin_a * cos_b,   (sin_a * sin_b * sin_g) + (cos_a * cos_g),   (sin_a * sin_b * cos_g) - (cos_a * sin_g)  },
                                                {-sin_b       ,    cos_b * sin_g                           ,   cos_b * cos_g                              }    };

             double new_x_calc_1 = RotationMatrix[0][0]   *   PositionMatrix[0];
             double new_x_calc_2 = RotationMatrix[0][1]   *   PositionMatrix[1];
             double new_x_calc_3 = RotationMatrix[0][2]   *   PositionMatrix[2];        

             double new_y_calc_1 = RotationMatrix[1][0]   *   PositionMatrix[0];
             double new_y_calc_2 = RotationMatrix[1][1]   *   PositionMatrix[1];
             double new_y_calc_3 = RotationMatrix[1][2]   *   PositionMatrix[2];           

             double new_z_calc_1 = RotationMatrix[2][0]   *   PositionMatrix[0];
             double new_z_calc_2 = RotationMatrix[2][1]   *   PositionMatrix[1];
             double new_z_calc_3 = RotationMatrix[2][2]   *   PositionMatrix[2];         

             new_rotated_x = (float)(new_x_calc_1 + new_x_calc_2 + new_x_calc_3);
             new_rotated_y = (float)(new_y_calc_1 + new_y_calc_2 + new_y_calc_3);
             new_rotated_z = (float)(new_z_calc_1 + new_z_calc_2 + new_z_calc_3);
             
        return(new Vector3f(new_rotated_x+centerPoint.x, new_rotated_y+centerPoint.y, new_rotated_z+centerPoint.z));
    }

}
