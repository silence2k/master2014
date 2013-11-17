package bastel2;

public class Physics_old {
	static float new_rotated_x, new_rotated_y, new_rotated_z;
    static float last_point_x, last_point_y, last_point_z, last_alpha, last_beta, last_gamma;
    

    public static float RotationMatrix(String pointtype,float point_x,float point_y,float point_z, float center_point_x, float center_point_y, float center_point_z, float alpha, float beta, float gamma)
    {
        //pointtype:                                       either, "x","y" or "z" depending on the axis you want to return
        //point_x,point_y,point_z:                         the coordinates of the point that rotates - coordinates of the center point
        //center_point_x,center_point_y,center_point_z:    the the coordinates of the center point (the point revolves around the center point)
        //rotation alpha,beta,gamma                        rotation alpha,beta,gamma
        
        /* 3D Rotation matrix 
        * 
        *  R(alpha,beta,gamma) = Rz (alpha) Ry (beta) Rx (gamma)
        * 
        *  | [cos_a * cos_b]   [cos_a * sin_b * sin_g - sin_a * cos_g]     [cos_a * sin_b * cos_g + sin_a * sin_g] |
        *  | [sin_a * cos_b]   [sin_a * sin_b * sin_g + cos_a * cos_g]     [sin_a * sin_b * cos_g - cos_a * sin_g] |    
        *  | [-sin_b       ]   [cos_b * sin_g                        ]     [cos_b * cos_g                        ] |
        * 
        */
        
        //dont recalculate because x,y and z are already calculated before
        if (last_point_x == point_x && last_point_y == point_y && last_point_z == point_z && last_alpha == alpha && last_beta == beta && last_gamma == gamma)
        {

        }
        
        //recalculate
        else
        {
             double sin_a = Math.sin(alpha);
             double sin_b = Math.sin(beta);
             double sin_g = Math.sin(gamma);  

             double cos_a = Math.cos(alpha);
             double cos_b = Math.cos(beta);
             double cos_g = Math.cos(gamma);                                                                                                              double[] PositionMatrix = {   point_x,   point_y,   point_z};      

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
             
             last_point_x = point_x;
             last_point_y = point_y;
             last_point_z = point_z;
             last_alpha = alpha;
             last_beta = beta;
             last_gamma = gamma;
        }
        
        if (pointtype.contains("x"))
        {
            return(new_rotated_x+center_point_x);
        }
        else if (pointtype.contains("y"))
        {
            return(new_rotated_y+center_point_y);
        }
        else if (pointtype.contains("z"))
        {
            return(new_rotated_z+center_point_z);
        }
        return(0);
    }
}
