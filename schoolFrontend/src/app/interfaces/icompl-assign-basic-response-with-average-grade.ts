export interface IComplAssignBasicResponseWithAverageGrade {
  averageGrade: number;
  completedAssignments: { assignmentId: number; grade: number }[];
}
